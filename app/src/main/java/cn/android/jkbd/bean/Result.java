package cn.android.jkbd.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/28.
 */

public class Result implements Serializable{

    /**
     * error_code : 0
     * reason : ok
     * result : [{"id":9,"question":"这个标志是何含义？","answer":"2","item1":"距有人看守铁路道口50米","item2":"距无人看守铁路道口100米","item3":"距有人看守铁路道口100米","item4":"距无人看守铁路道口50米","explains":"一道红线是50米，二道100米。","url":"http://images.juheapi.com/jztk/c1c2subject1/9.jpg"}]
     */

    private int error_code;
    private String reason;
    private List<Qusetion> result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<Qusetion> getResult() {
        return result;
    }

    public void setResult(List<Qusetion> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Result{" +
                "error_code=" + error_code +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                '}';
    }
}
