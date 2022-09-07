package io.agora.secnceui.widget.wheat

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
import io.agora.secnceui.bean.SeatInfoBean
import io.agora.secnceui.databinding.ViewChatroom3dAudioWheatBinding
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.hypot

class ChatroomWheat3DAudioView : ConstraintLayout, View.OnClickListener {

    companion object {
        const val TAG = "ChatroomWheat3DAudioView"

        // 偏移角度误差,左右10度
        const val OFFSET_ANGLE = 10

        // 时间间隔
        const val _INTERVAL = 200

        // 时间间隔
        const val TIME_INTERVAL = 200
    }

    private lateinit var binding: ViewChatroom3dAudioWheatBinding

    private val constraintSet = ConstraintSet()

    private var lastX = 0
    private var lastY = 0

    // 上一次移动坐标(中心圆点)
    private val preMovePoint = Point(0, 0)

    // 是否触摸移动
    private var isMove = false

    // 座位view 尺寸
    private val seatSize by lazy {
        Size(binding.seatViewCenter.width, binding.seatViewCenter.height)
    }

    // 可点击区域尺寸
    private val rootSize by lazy {
        Size(binding.root.width, binding.root.height)
    }

    // 上一次角度
    private var preAngle: Double = 0.0

    // 上一次移动的时间
    private var preTime: Long = 0

    // 点按动画
    private var seatAnimator: ValueAnimator? = null

    private var onItemClickListener: OnItemClickListener<SeatInfoBean>? = null

    fun onItemClickListener(onItemClickListener: OnItemClickListener<SeatInfoBean>) = apply {
        this.onItemClickListener = onItemClickListener
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
        val root = View.inflate(context, R.layout.view_chatroom_3d_audio_wheat, this)
        binding = ViewChatroom3dAudioWheatBinding.bind(root)
        constraintSet.clone(binding.root)
        initListeners()
        post {
            // 当前移动的坐标圆点
            preMovePoint.x = binding.seatViewCenter.left + seatSize.width / 2
            preMovePoint.y = binding.seatViewCenter.top + seatSize.height / 2
        }
    }

    private fun initListeners() {
        binding.seatView1.setOnClickListener(this)
        binding.seatView2.setOnClickListener(this)
        binding.seatView3.setOnClickListener(this)
        binding.seatView5.setOnClickListener(this)
        binding.seatView6.setOnClickListener(this)
        binding.seatView7.setOnClickListener(this)
        binding.seatViewCenter.setOnClickListener(this)
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
            R.id.seatView1 -> {
                onItemClickListener?.onItemClick(SeatInfoBean(),v,1,-1)
            }
            R.id.seatView2 -> {
                onItemClickListener?.onItemClick(SeatInfoBean(),v,2,-1)
            }
            R.id.seatView3 -> {
                onItemClickListener?.onItemClick(SeatInfoBean(),v,3,-1)
            }
            R.id.seatView5 -> {
                onItemClickListener?.onItemClick(SeatInfoBean(),v,5,-1)
            }
            R.id.seatView6 -> {
                onItemClickListener?.onItemClick(SeatInfoBean(),v,6,-1)
            }
            R.id.seatView7 -> {
                onItemClickListener?.onItemClick(SeatInfoBean(),v,7,-1)
            }
            R.id.seatViewCenter -> {
                onItemClickListener?.onItemClick(SeatInfoBean(),v,4,-1)
            }
        }
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        val x = event.x.toInt()
        val y = event.y.toInt()
        //拦截3d 座位
        if (check3DSeatChildView(x, y)) {
            return true
        }
        return super.onInterceptTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //获取到手指处的横坐标和纵坐标
        val x = event.x.toInt()
        val y = event.y.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                seatAnimator?.cancel()
                seatAnimator = null
                lastX = x
                lastY = y
                isMove = false
                "onTouchEvent ACTION_DOWN x:${x} y:${y}".logE()
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = (x - lastX)
                val dy = (y - lastY)
                lastX = x
                lastY = y
                if (!checkInnerArea(x, y)) {
                    return false
                }
                binding.seatViewCenter.translationX += dx
                binding.seatViewCenter.translationY += dy
                isMove = true
                // 计算箭头角度
                val curTime = SystemClock.elapsedRealtime()
                if (curTime - preTime >= TIME_INTERVAL) {
                    preTime = curTime
                    // 当前移动的坐标圆点
                    val curPoint = Point(x, y)
                    // 移动的角度
                    val angle = getAngle(curPoint, preMovePoint)
                    if (abs(angle - preAngle) > OFFSET_ANGLE) {
                        binding.seatViewCenter.changeAngle(angle.toFloat())
                        preMovePoint.x = curPoint.x
                        preMovePoint.y = curPoint.y
                        preAngle = angle
                    }
                    "onTouchEvent ACTION_MOVE x:${x} y:${y} dx:${dx} dy:${dy} angle:${angle}".logE()
                }
                return  true
            }
            MotionEvent.ACTION_UP -> {
                if (!isMove) {
                    if (!checkInnerArea(x, y)) {
                        "onTouchEvent ACTION_UP OuterArea".logE()
                        seatAnimator?.cancel()
                        seatAnimator = null
                        return false
                    }
                    // 视角效果左上角坐标
                    val seatVisualPoint = PointF(binding.seatViewCenter.x, binding.seatViewCenter.y)
                    val dx = (x - seatSize.width / 2 - seatVisualPoint.x)
                    val dy = (y - seatSize.height / 2 - seatVisualPoint.y)
                    // 点按增加偏移误差
                    if (ignoreSmallOffsets(dx, dy)) {
                        return false
                    }
                    // 移动相对距离
                    val dz = hypot(dx, dy)

                    // 已经移动距离
                    val seatTranslated =
                        PointF(binding.seatViewCenter.translationX, binding.seatViewCenter.translationY)

                    binding.seatViewCenter.animate()
                        .translationX(seatTranslated.x + dx)
                        .translationY(seatTranslated.y + dy)
                        .setDuration((dz*1.5).toLong())
                        .setUpdateListener { animator ->
                            seatAnimator = animator
                        }
                        .start()
                    // 当前移动的坐标圆点
                    val curPoint = Point(x, y)
                    // 移动的角度
                    val angle = getAngle(curPoint, preMovePoint)
                    binding.seatViewCenter.changeAngle(angle.toFloat())
                    preMovePoint.x = curPoint.x
                    preMovePoint.y = curPoint.y
                    preAngle = angle
                    "onTouchEvent ACTION_UP x:${x} y:${y} dx:${dx} dy:${dy} z:${dz} angle:${angle}".logE()
                }
            }
        }
        return super.onTouchEvent(event)
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

    /**
     * 检测点击/移动坐标是否在区域内
     * @param x x
     * @param y y
     */
    private fun checkInnerArea(x: Int, y: Int): Boolean {
        val moveXInner = (x >= seatSize.width / 2 && x <= rootSize.width - seatSize.width / 2)
        val moveYInner = (y >= seatSize.height / 2 && y <= rootSize.height - seatSize.height / 2)
        return moveXInner && moveYInner
    }

    private fun ignoreSmallOffsets(dx: Float, dy: Float): Boolean {
        return abs(dx) < 10f && abs(dy) < 10f
    }

    /**
     * 是否有是3d 座位移动
     */
    private fun check3DSeatChildView(x: Int, y: Int): Boolean {
        if (getRect(binding.seatViewCenter).contains(x.toFloat(), y.toFloat())) {
            "onTouchEvent ACTION_DOWN checkChildView:${x} ${y}".logE()
            return true
        }
        return false
    }

    private fun setChildView(childView: View, isClickable: Boolean) {
        childView.isClickable = isClickable
    }
}