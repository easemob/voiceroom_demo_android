package manager;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;
import io.agora.CallBack;
import io.agora.chat.ChatClient;
import io.agora.chat.ChatOptions;

public class ChatroomConfigManager {
    private static final String TAG = ChatroomConfigManager.class.getSimpleName();
    private static ChatroomConfigManager mInstance;
    private Context mContext;

    ChatroomConfigManager(){}

    public static ChatroomConfigManager getInstance() {
        if (mInstance == null) {
            synchronized (ChatroomConfigManager.class) {
                if (mInstance == null) {
                    mInstance = new ChatroomConfigManager();
                }
            }
        }
        return mInstance;
    }

    public void initRoomConfig(Context context){
        this.mContext = context;
        ChatOptions options = initChatOptions(context);
        if (!isMainProcess(context)){
            Log.e(TAG, "enter the service process!");
            return;
        }
        ChatClient.getInstance().init(context,options);

    }

    private ChatOptions initChatOptions(Context context){
        ChatOptions options = new ChatOptions();
        options.setAppKey("52117440#955012");
        options.setAutoLogin(false);
        return options;
    }

    private boolean isMainProcess(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return context.getApplicationInfo().packageName.equals(appProcess.processName);
            }
        }
        return false;
    }


}
