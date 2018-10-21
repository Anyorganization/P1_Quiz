package lospros.com.androidquiz;

public class Question {
    private String question = null;
    private String type;
    private String path =  null;
    private int cA;
    private boolean iA;//text (false) or images (true) on Answers
    private  String [] ans = new String[4];//Text or name of images.

    public Question(){
    }


    public Question(String question, String path, int cA, boolean iA, String[] ans) {
        this.question = question;
        if(path.equals("")){
            this.path = path;
        }else{
            this.path = null;
        }
        this.cA = cA;
        this.iA = iA;
        this.ans = ans;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getcA() {
        return cA;
    }

    public void setcA(int cA) {
        this.cA = cA;
    }

    public boolean isiA() {
        return iA;
    }

    public void setiA(boolean iA) {
        this.iA = iA;
    }

    public String[] getAns() {
        return ans;
    }

    public void setAns(String[] ans) {
        this.ans = ans;
    }

    public String getType() { return type;  }

    public void setType(String type) { this.type = type; }
}
