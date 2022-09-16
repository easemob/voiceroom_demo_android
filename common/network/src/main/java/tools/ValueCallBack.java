package tools;

public interface ValueCallBack<T> {
    void onSuccess(T var1);

    void onError(int var1, String var2);
}