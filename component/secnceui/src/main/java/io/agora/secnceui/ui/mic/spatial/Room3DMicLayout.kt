package io.agora.secnceui.ui.mic.spatial

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Point
import android.graphics.PointF
import android.graphics.RectF
import android.os.SystemClock
import android.util.AttributeSet
import android.util.Size
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import io.agora.baseui.adapter.OnItemClickListener
import io.agora.buddy.tool.logE
import io.agora.secnceui.R
import io.agora.secnceui.bean.MicInfoBean
import io.agora.secnceui.constants.ScenesConstant
import io.agora.secnceui.databinding.ViewChatroom3dMicLayoutBinding
import io.agora.secnceui.ui.mic.IRoomMicView
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.hypot

class Room3DMicLayout : ConstraintLayout, View.OnClickListener, IRoomMicView {

    companion object {
        const val TAG = "Room3DMicLayout"

        // 偏移角度误差,左右10度
        const val OFFSET_ANGLE = 10

        // 时间间隔
        const val TIME_INTERVAL = 100
    }

    private lateinit var binding: ViewChatroom3dMicLayoutBinding

    private val constraintSet = ConstraintSet()

    private var lastX = 0
    private var lastY = 0

    // 上一次移动坐标(中心圆点)
    private val preMovePoint = Point(0, 0)

    // 是否触摸移动
    private var isMove = false

    // spatialView 尺寸
    private val micViewSize by lazy {
        Size(binding.micViewCenter.width, binding.micViewCenter.height)
    }

    // rootView尺寸
    private val rootSize by lazy {
        Size(binding.root.width, binding.root.height)
    }

    // 3d 座位最大移动距离
    private val maxTranslationScope by lazy {
        Size(
            binding.root.width / 2 - binding.micViewCenter.width / 2,
            binding.root.height / 2 - binding.micViewCenter.height / 2
        )
    }

    // 上一次角度
    private var preAngle: Double = 0.0

    // 上一次移动的时间
    private var preTime: Long = 0

    // 点按动画
    private var micClickAnimator: ValueAnimator? = null

    private var onItemClickListener: OnItemClickListener<MicInfoBean>? = null
    private var onBotClickListener: OnItemClickListener<MicInfoBean>? = null

    private val micInfoMap = mutableMapOf<String, MicInfoBean>()

    fun onItemClickListener(
        onItemClickListener: OnItemClickListener<MicInfoBean>,
        onBotClickListener: OnItemClickListener<MicInfoBean>
    ) = apply {
        this.onItemClickListener = onItemClickListener
        this.onBotClickListener = onBotClickListener
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context, attrs, defStyleAttr, defStyleRes
    ) {
        init(context)
    }

    private fun init(context: Context) {
        val root = View.inflate(context, R.layout.view_chatroom_3d_mic_layout, this)
        binding = ViewChatroom3dMicLayoutBinding.bind(root)
        constraintSet.clone(binding.root)
        initListeners()
        post {
            // 当前移动的坐标圆点
            preMovePoint.x = binding.micViewCenter.left + micViewSize.width / 2
            preMovePoint.y = binding.micViewCenter.top + micViewSize.height / 2
        }
    }

    fun setUpMicInfoMap(micInfoList: MutableMap<String, MicInfoBean>) {
        this.micInfoMap.putAll(micInfoList)
        micInfoMap[ScenesConstant.KeyMic0]?.let {
            binding.micView0.binding(it)
        }
        micInfoMap[ScenesConstant.KeyMic1]?.let {
            binding.micView1.binding(it)
        }
        micInfoMap[ScenesConstant.KeyMic3]?.let {
            binding.micView3.binding(it)
        }
        micInfoMap[ScenesConstant.KeyMic4]?.let {
            binding.micView4.binding(it)
        }
        micInfoMap[ScenesConstant.KeyMicBlue]?.let {
            binding.micRotBlue.binding(it)
        }
        micInfoMap[ScenesConstant.KeyMicRed]?.let {
            binding.micBotRed.binding(it)
        }
        micInfoMap[ScenesConstant.KeyMicCenter]?.let {
            binding.micViewCenter.binding(it)
        }
    }

    private fun initListeners() {
        binding.micView0.setOnClickListener(this)
        binding.micView1.setOnClickListener(this)
        binding.micView3.setOnClickListener(this)
        binding.micView4.setOnClickListener(this)
        binding.micRotBlue.setOnClickListener(this)
        binding.micBotRed.setOnClickListener(this)
        binding.micViewCenter.setOnClickListener(this)
    }

    private fun getRect(view: View): RectF {
        return RectF(
            view.x,
            view.y + rootView.y,
            view.x + view.width,
            view.y + rootView.y + view.height
        )
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.micView0 -> {
                micInfoMap[ScenesConstant.KeyMic0]?.let {
                    onItemClickListener?.onItemClick(it, v, 0, -1)
                }
            }
            R.id.micView1 -> {
                micInfoMap[ScenesConstant.KeyMic1]?.let {
                    onItemClickListener?.onItemClick(it, v, 1, -1)
                }
            }
            R.id.micView3 -> {
                micInfoMap[ScenesConstant.KeyMic3]?.let {
                    onItemClickListener?.onItemClick(it, v, 3, -1)
                }
            }
            R.id.micView4 -> {
                micInfoMap[ScenesConstant.KeyMic4]?.let {
                    onItemClickListener?.onItemClick(it, v, 4, -1)
                }
            }
            R.id.micRotBlue -> {
                micInfoMap[ScenesConstant.KeyMicBlue]?.let {
                    onBotClickListener?.onItemClick(it, v, 2, -1)
                }
            }
            R.id.micBotRed -> {
                micInfoMap[ScenesConstant.KeyMicRed]?.let {
                    onBotClickListener?.onItemClick(it, v, 5, -1)
                }
            }
            R.id.micViewCenter -> {
                micInfoMap[ScenesConstant.KeyMicCenter]?.let {
                    onItemClickListener?.onItemClick(it, v, 6, -1)
                }
            }
        }
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        val x = event.x.toInt()
        val y = event.y.toInt()
        //拦截3d 座位
        if (check3DMicChildView(x, y)) {
            return if (event.action == MotionEvent.ACTION_MOVE) {
                true
            } else {
                super.onInterceptTouchEvent(event)
            }
        }
        return super.onInterceptTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //获取到手指处的横坐标和纵坐标
        val x = event.x.toInt()
        val y = event.y.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                micClickAnimator?.cancel()
                micClickAnimator = null
                lastX = x
                lastY = y
                isMove = false
                "onTouchEvent ACTION_DOWN x:${x} y:${y}".logE(TAG)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = (x - lastX)
                val dy = (y - lastY)
                lastX = x
                lastY = y

                val nextTransitionX = binding.micViewCenter.translationX + dx
                val nextTransitionY = binding.micViewCenter.translationY + dy
                if (abs(nextTransitionX) > maxTranslationScope.width || abs(nextTransitionY) > maxTranslationScope.height) {
                    return false
                }
                binding.micViewCenter.translationX = nextTransitionX
                binding.micViewCenter.translationY = nextTransitionY
                isMove = true
                // 计算箭头角度
                val curTime = SystemClock.elapsedRealtime()
                if (curTime - preTime >= TIME_INTERVAL) {
                    preTime = curTime
                    // 当前准备移动到的坐标圆点
                    val curPoint = Point(
                        binding.micViewCenter.left + nextTransitionX.toInt() + micViewSize.width / 2,
                        binding.micViewCenter.top + nextTransitionY.toInt() + micViewSize.height / 2
                    )
                    // 移动的角度
                    val angle = getAngle(curPoint, preMovePoint)
                    if (abs(angle - preAngle) > OFFSET_ANGLE) {
                        binding.micViewCenter.changeAngle(angle.toFloat())
                        preMovePoint.x = curPoint.x
                        preMovePoint.y = curPoint.y
                        preAngle = angle
                    }
                    "onTouchEvent ACTION_MOVE x:${x} y:${y} dx:${dx} dy:${dy} angle:${angle}".logE(TAG)
                }
                return true
            }
            MotionEvent.ACTION_UP -> {
                if (!isMove) {
                    // 保证3d 座位移动不超过rootView区域
                    val correctedX = correctMotionEventX(x)
                    val correctedY = correctMotionEventY(y)
                    // 视角效果左上角坐标
                    val micVisualPoint = PointF(binding.micViewCenter.x, binding.micViewCenter.y)
                    val dx = (correctedX - micViewSize.width / 2 - micVisualPoint.x)
                    val dy = (correctedY - micViewSize.height / 2 - micVisualPoint.y)
                    // 点按增加偏移误差
                    if (ignoreSmallOffsets(dx, dy)) return false
                    // 移动相对距离
                    val dz = hypot(dx, dy)
                    // 已经移动距离
                    val translatedX = binding.micViewCenter.translationX
                    val translatedY = binding.micViewCenter.translationY

                    binding.micViewCenter.animate()
                        .translationX(translatedX + dx)
                        .translationY(translatedY + dy)
                        .setDuration((dz * 1.5).toLong())
                        .setUpdateListener { animator ->
                            micClickAnimator = animator
                        }
                        .start()
                    // 当前移动的坐标圆点
                    val curPoint = Point(correctedX, correctedY)
                    // 移动的角度
                    val angle = getAngle(curPoint, preMovePoint)
                    binding.micViewCenter.changeAngle(angle.toFloat())
                    preMovePoint.x = curPoint.x
                    preMovePoint.y = curPoint.y
                    preAngle = angle
                    "onTouchEvent ACTION_UP x:${x} y:${y} dx:${dx} dy:${dy} z:${dz} angle:${angle}".logE(TAG)
                }
            }
            MotionEvent.ACTION_CANCEL -> {}
        }
        return super.onTouchEvent(event)
    }

    // 纠正x偏差
    private fun correctMotionEventX(x: Int): Int {
        if (x < micViewSize.width / 2) return micViewSize.width / 2
        if (x > rootSize.width - micViewSize.width / 2) return rootSize.width - micViewSize.width / 2
        return x
    }

    // 纠正y偏差
    private fun correctMotionEventY(y: Int): Int {
        if (y < micViewSize.height / 2) return micViewSize.height / 2
        if (y > rootSize.height - micViewSize.height / 2) return rootSize.height - micViewSize.height / 2
        return y
    }

    private fun getAngle(curP: Point, preP: Point): Double {
        val changeX = curP.x - preP.x
        val changeY = curP.y - preP.y
        // 用反三角函数求弧度
        val radina = atan2(changeY.toDouble(), changeX.toDouble())
        // 将弧度转换成角度,需要加90
        val angle = 180.0 / Math.PI * radina
        return angle + 90
    }

    private fun ignoreSmallOffsets(dx: Float, dy: Float): Boolean {
        return abs(dx) < 10f && abs(dy) < 10f
    }

    /**
     * 是否有是3d 座位移动
     */
    private fun check3DMicChildView(x: Int, y: Int): Boolean {
        if (getRect(binding.micViewCenter).contains(x.toFloat(), y.toFloat())) {
            "onTouchEvent ACTION_DOWN checkChildView:${x} ${y}".logE(TAG)
            return true
        }
        return false
    }

    private fun setChildView(childView: View, isClickable: Boolean) {
        childView.isClickable = isClickable
    }

    override fun updateAdapter(micInfoList: List<MicInfoBean>, isBotActive: Boolean) {

    }

    override fun activeBot(active: Boolean) {

    }

    override fun updateVolume(index: Int, volume: Int) {

    }

    override fun updateBotVolume(speakerType: Int, volume: Int) {

    }
}