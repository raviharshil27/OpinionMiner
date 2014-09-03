package opinionEngine.sentimentAnalyzer;

import lexiconDictionary.sentiWordNetMongo;
import lexiconDictionary.sentiWordNetVector;
import lexiconDictionary.sentiwordNET;
import partOfSpeechTagger.openNlp;
public class lexiconBaseAnalysis
{
    public static void main(String a[])
    {
        lexiconBaseAnalysis l=new lexiconBaseAnalysis();
        l.getStmtPolarity("i just love iphone5 camera  too much.");
    }
    public double getStmtPolarity(String statement)
    {
        String stmt_word[]=openNlp.getWords(statement);
        String stmt_pos[]=openNlp.getTokens(statement);
        Double stmt_ans[] = null;
        boolean adverbnegation=false;
        int i = 0;
        stmt_ans = new Double[stmt_word.length];
        Double total = 0.0;
        System.out.println("polarity check!");
        //get polarity
        while (i < stmt_word.length)
        {
            stmt_ans[i] = sentiWordNetMongo.getPolarity(stmt_word[i],stmt_pos[i]);
            if(stmt_ans[i]==null)
                stmt_ans[i]=0.0;
//            if(stmt_ans[i]>0)
//             System.out.println(stmt_word[i] + " => " + "positive " +stmt_ans[i]);
//            else if(stmt_ans[i]<0)
//             System.out.println(stmt_word[i] + " => " + "negative " +stmt_ans[i]);
//            else
//             System.out.println(stmt_word[i] + " => " + "neutral " +stmt_ans[i]);
            i++;
        }
        //negation rule
        for (int j=0;j<stmt_word.length;j++)
        {
            if(stmt_pos[j].equals("r") && stmt_ans[j]<0.0 && adverbnegation==false)
            {
                adverbnegation=true;
                System.out.println("negation found !");
                continue;
            }
            if(stmt_pos[j].equals("r") && stmt_ans[j]<0.0 && adverbnegation==true)
            {
                adverbnegation=false;
                continue;
            }
                if(adverbnegation==true)
                 stmt_ans[j]=-stmt_ans[j];
                total+=stmt_ans[j];
        }
        //context dependent word handling
        System.out.println("total polarity count: "+total);
//        if (total >= 0.5)
//            return 2;
//        else if (total>0.0 && total<0.5)
//            return 1;
//        else if(total==0.0)
//            return 0;
//        else if(total<0.0 && total>-0.5)
//            return -1;
//        else 
//            return -2;
//        
            return (total);
    }
    
}
