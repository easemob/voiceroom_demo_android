package io.agora.secnceui.widget.wheat

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import io.agora.secnceui.R
import io.agora.secnceui.databinding.ViewChatroom3dAudioWheatBinding

class ChatroomWheat3DAudioView : ConstraintLayout {

    private lateinit var binding: ViewChatroom3dAudioWheatBinding

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
    }
}