import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    OBJECTIVE
    Given a pool of 600 questions generate as many quizzes as possible

    CONDITIONS
     Each question belongs to any one of the 6 tags - tag1, tag2 ......tag6
     Each question belongs to any one of the difficulty level - EASY, MEDIUM or HARD
     A question can be used only once in any quiz.

    Example:
    Say from a pool of 600 questions, after the first quiz computation, only 590 questions will be
    available for the next quiz


    CRITERIA TO GENERATE A QUIZ
     1 question from each tag
     2 questions from each difficulty level
     10 questions per quiz
*/

public class Main {

    public static void main(String[] args) {
        List<String> dataList = new ArrayList<>();
        String strLine;
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/q.txt"));
            while (true)
            {
                strLine = br.readLine();
                if (strLine==null)
                    break;
                dataList.add(strLine);
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        } catch (IOException e) {
            System.err.println("Unable to read the file.");
        }
        HashMap<String, List<Question>> tagsToQuestion = new HashMap<>();
        HashMap<String, List<Question>> levelsToQuestion = new HashMap<>();
        int total = dataList.size();
        for (String d : dataList) {
            String[] x = d.split("\\s*\\|\\s*");
            Question q = new Question(x[0], x[1], x[2]);
            if (!tagsToQuestion.containsKey(q.getTag())) {
                List<Question> ql = new ArrayList<>();
                ql.add(q);
                tagsToQuestion.put(q.getTag(), ql);
            } else {
                tagsToQuestion.get(q.getTag()).add(q);
            }
            if (!levelsToQuestion.containsKey(q.getLevel())) {
                List<Question> ql = new ArrayList<>();
                ql.add(q);
                levelsToQuestion.put(q.getLevel(), ql);
            } else {
                levelsToQuestion.get(q.getLevel()).add(q);
            }
        }

        if (tagsToQuestion.size() == 6 && levelsToQuestion.size() == 3) {
            int minLevel = total;
            for (Map.Entry<String, List<Question>> levelsEntry : levelsToQuestion.entrySet()) {
                if (minLevel > levelsEntry.getValue().size()/2) {
                    minLevel = levelsEntry.getValue().size()/2;
                            //Math.min(levelsEntry.getValue().size()/2, (total - levelsEntry.getValue().size())/8);
                }
            }
            int minTag = minLevel;
            for (Map.Entry<String, List<Question>> tagEntry : tagsToQuestion.entrySet()) {
                if (minTag > tagEntry.getValue().size()) {
                    minTag = tagEntry.getValue().size();
                            //Math.min(tagEntry.getValue().size(), (total - tagEntry.getValue().size())/9);
                }
            }
            System.out.println(Math.min(2,minTag));
        } else {
            System.out.println(0);
        }
    }
}
