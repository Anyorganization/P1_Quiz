package lospros.com.androidquiz;

public class Question {
    private String question = null;
    private String image =  null;
    private int cA;

    private boolean iA;//text (false) or images (true) on Answers
    private  String [] ans = new String[4];//Text or name of images.

    public Question(){
    }


    public Question(String question, String image, int cA, boolean iA, String[] ans) {
        this.question = question;
        if(image.equals("")){
            this.image = image;
        }else{
            this.image = null;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
