
public enum ChatroomType {
    NORMAL(0),
    SPATIAL(1);

    //在枚举中定义常量
    private static final String TAG = "Avatar";

    private final int type;

    ChatroomType(int type) {
        this.type = type;
    }
}


