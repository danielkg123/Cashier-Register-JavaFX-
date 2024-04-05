package Repository;

public class DataSingleton {
    private static final DataSingleton instance = new DataSingleton();
    private String loginDetail;
    private DataSingleton() {
    }

    public static DataSingleton getInstance(){
        return instance;
    }

    public String getLoginDetail() {
        return loginDetail;
    }

    public void setLoginDetail(String loginDetail) {
        this.loginDetail = loginDetail;
    }
}
