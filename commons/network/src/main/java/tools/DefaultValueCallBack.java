package tools;

public interface DefaultValueCallBack<T> extends ValueCallBack<T>{

    @Override
    default void onSuccess(T var1){}

    @Override
    default void onError(int var1, String var2){}
}