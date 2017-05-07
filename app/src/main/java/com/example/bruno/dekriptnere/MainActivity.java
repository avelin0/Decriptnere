package com.example.bruno.dekriptnere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ReadFile files;
    List<Dictionaries> listOfLanguages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
//            read files and get Lists of Languages
            this.files = new ReadFile("Languages", getApplicationContext());
            this.listOfLanguages = this.files.toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Analyse(View view) throws IOException {

//        input
        Switch sw = (Switch) findViewById(R.id.switch1);

        EditText lCipherText = (EditText) findViewById(R.id.lCipher);
        TextView lKeySizeText = (TextView) findViewById(R.id.lKeySize);
        TextView lLanguageText = (TextView) findViewById(R.id.lLang);
        TextView lKeyText = (TextView) findViewById(R.id.lKey);
        TextView lPlainText = (TextView) findViewById(R.id.lPlainText);

        Kasiski cript;

//        Input -> somente letras sem numeros de comprimento >= 13
        String cipher = lCipherText.getText().toString();
        String validCipher = isValid(cipher);

        switch (validCipher) {
            case "Valid" :
                cript = new Kasiski( cipher.toUpperCase(), this.listOfLanguages);
                break;
            default:
                cript = new Kasiski(("tzozpkswtfpdbrsjvnasqewfovptzsgnbqyvvcspmmlspsdpkknoypozpnohezy" +
                        "vghuozzdhvzjovbohjaojbtostfbpenolbifzfvcsqmxwzohxpvgzdlzylsngftjkocqsjvxxjuo" +
                        "mrjbetzxllseqljzklfzjdpsdpkknoypbgoboyodgohsqmwmnzyrzvvbofcwcnkqddkbveszfpbw" +
                        "ilffbcoevoeqsbtyjohsmoaefoirgqyoypbgomwltvgyhsqgarqhvcgoovppvjvwsidygisdunsy" +
                        "mueoucgzppozpuwwslfvmlzylsnrqccpzgdtzuluzuofkbvemiveqsugwpdbrmiveqsczjpdbriz" +
                        "jpcvuqatnhomtypwsitkcirrayklrrbplvcscqwwlowhxsozbeuilsnayokvjtetzolcshdspyzp" +
                        "fdlorjyowvcshmowcbthzevcshmowcbuuywudglzyyzmaqowvcsqumelvsdespyrthdvpmhxpoco" +
                        "scerztlvmpjgpboqmlsntycechsyfajzvhxpocoscerztlvmpjgvpzhzlsntycechsyfvforhmlk" +
                        "ujupzzktbodoyqyqlxgwocvuqathoxqilsnolpfciretzwgnbyyyciretzezabyyyyzfpfcwdnqe" +
                        "yvfvmrqiwdrgqyvijrdmdvwnhjswyvhpdnmymshezgcslhzfmnuqezgmsofjypcvucmpoczzzhwj" +
                        "qulffgsefcwoamblffvdaqvjlwryeocngzszfpbwilffbcoovdwnrjswfmmwmivpjfjsspyhsqbs" +
                        "eqshtfiocrqozpacvezgroeqmknjzbpvjzgpmnsymueokcrhsmoaefoirgqyupzzktbodoyqyglu" +
                        "ydpchxpwcmhsnmayptecljbflenlsnvuctadswpdfrbsuospyhsqajfrhjcwgtwpxyaypthfavvt" +
                        "eqmztbyyyvyccdqnwpmwitfkogpxamaxbjswgvfetvforhmlkujupzzktbodoljzsldozmackrzv" +
                        "accfcycjgilffcscntapuryyyuzsomalpavydckirlzylsnhhpwadswpdfrofktlyccdqnwpmkqd" +
                        "apdhdqgxlohuczknytzysymueokcrhsmoaefoirgqyupzzktbodoljzsgqiaypodoljzazdiaypk" +
                        "ucwvcsetdjomoorwpzgtevfopctdskyzpfozpasrpdkbveedfeqsvtjovapzogqcvuswcqsyfjvt" +
                        "ewtpljzrlkajzvhxpfkbvemivwnhjswowsqamktpbilffaccezsdxbilffaccpvqdjbtjwcmgrqi" +
                        "wdrgqyvnzhetzemntecdkbveedfeqsvtjovapzogqcvuswcqsyfjytesbtyjoiaailsnsqcljvbo" +
                        "uoolbgerwpzgtevfopctxsfzhhabjpjhbtyjogetzycnojpjndusfogcdzuezgyojmiveqsbpkuz" +
                        "fwubzecchfdgovpzdyscvuxsfzhsqnllagqwkqbsyqnadjbtrgfnsefcwxrbjswhdfxmhwyccvez" +
                        "gcslhzfexuygwndusfphzwhxpwcmhsszfpbwilffoccggwzeshezgyojmivzeshezgiwrtosymhe" +
                        "oaxdrpfcwwruxextjaetzvlaydpkuvbosjvdjkjssvdhhmnyzxrwpfgnwdmiveqsugwpdbrmiveq" +
                        "sczjpdbrizjpcvuqgwmhspvqrnbudauvbosjvdjwtwwvovpivlpagrcapbtzdozlkidospozjfcw" +
                        "xxjyyyemslfpjpcvqezcovwuawlwrvzonovlfhsjozoltqqsetzwlahxtfvcszbzfqrfclegihzr" +
                        "cwlesdrwpzgtevfopctnjgvhppbjpjhmssnzglzywgnfowaxdbromwlcihpljvhxaqweqkxtujov" +
                        "pivlpagrcgwbverjjeqorfffvbextsqcshezgdfvuivlwrugwttktzbwoocmwshosctdkvrbtlff" +
                        "bcoevoeqojtlyvgrajvrnbudauvbosjvmusidwfovpynsjrbwmwhmitfamwjbtxmnowaxtsymtyw" +
                        "dvcshmowcbwdezgnslevfousjqgygafxoaaumyyljzsldozrnbudauvbofcwpesdtfivbofcwxxf" +
                        "dtfirscqozpowvezfvmrqiwdrgqyvijrdmdvwnhjswgvfetwjtwuvzjvchsqgagrbwnjgvhfdzsq" +
                        "cshsaufwypxseczulffxfpqkayphxtfivbonzsdccvezgzocfcsqcshsaufwypvforhmlkujupzz" +
                        "ktbodoyqyalpzlsnpulkvjtetzwlahxlxvzfsunctwrqyvevhexzsqcshezgdfvuivlwrugwtths" +
                        "uiyeqojnjgzdpfcmaxbjswgvfetvxenfxtkmdbomivrxrilovcoeuoolbuezvizbpedklwrwzvuv" +
                        "woxzlfbaqvwovbtzjmcraqrwcahpdjmcuwapfgnglzydpchxpejvjppjetwweygxzfetzxtbveql" +
                        "jzgpmvfoxjucljztziggqcvulatvboaqwccvunsvozpmivzeshldnovpqvjeqodogxzfphzjjlfu" +
                        "phkiuetdfrcvqeutzsaqozfycdezgzocfcypwsitkujuzpxjpjhuoeciwytdkzfbyxsizwyfcwtv" +
                        "owpghbcoomwlcstswjdaxmgwlwrvpecgsndzsenrxpljzarqiwdrgqyvijrmxzkdnrjswovbosjv" +
                        "djwtffvjhsqhtpofktlhpzlzyefuhyadavbodzhwnbydzvcspmmlsjbtdmdyipuosymvqgwfjatz" +
                        "dgyxjucljzttecgqcvudwcvboaqwccvuqgygcqfcwlrfqyvqqscqqwchzygapbhsuiyeqojxgxzh" +
                        "sgkgycvupstovrqiwdrgqyvijrdmdvmnvewvkcogqbagnbozmgqsckcwckpuljkiudqzvhqwssau" +
                        "pdzzozpoospghvzwfcwpjfjsspysgqmqojbtrgfnohfcs").toUpperCase(), this.listOfLanguages);
        }

        //        output
        lKeySizeText.setText(Integer.toString(cript.getKeyLength()));
        lKeyText.setText(cript.getKey());
        lLanguageText.setText(cript.getCriptoLanguage());
        lPlainText.setText(cript.getPlainText());
    }

    public String isValid(String pString){
        if(pString.isEmpty()){
            Toast.makeText(getBaseContext(),"Sua string não pode estar vazia.\nFoi aplicada a criptografia default do EAD.", Toast.LENGTH_LONG).show();
            return "nValid";
        }else if( containNum(pString) ){
            Toast.makeText(getApplicationContext(),"Sua String não pode ter números.\nFoi aplicada a criptografia default do EAD.", Toast.LENGTH_LONG).show();
            return "nValid";
        } else if(pString.length() < 13) {
            Toast.makeText(getApplicationContext(),"Sua String deve ter comprimento maior que 13.\nFoi aplicada a criptografia default do EAD.", Toast.LENGTH_LONG).show();
            return "nValid";
        } else{
            Toast.makeText(getApplicationContext(),"String Validada", Toast.LENGTH_LONG).show();
            return "Valid";
        }
    }


    boolean containNum(String arg){
        final String[] num = {"0","1","2","3","4","5","6","7","8","9"};
        String str = "";
        for(int i = 0; i < arg.length(); i++){
            str = Character.toString(arg.charAt(i));
            for(int j = 0; j < num.length; j++){
                if(str.equals(num[j])) return true;
            }
        }
        return false;
    }
}
