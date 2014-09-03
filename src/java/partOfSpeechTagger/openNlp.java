package partOfSpeechTagger;

import java.io.*;
import java.util.logging.*;
import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.*;
import opennlp.tools.postag.*;
import opennlp.tools.sentdetect.*;
import opennlp.tools.tokenize.*;

public class openNlp
{
    static String[] sentence;
    static String[] pos;
    static String[] tokens = null;
    static int i=0,j=0;
    static InputStream token_data = null;
    static InputStream pos_data = null;
    static InputStream sentence_data = null;
    static InputStream parsed_data=null;
    static TokenizerModel tm = null;
    static Tokenizer tokenizer = null;
    static POSModel pm =null;
    static SentenceModel sm = null;
    static ParserModel model=null;
    static Parser parser =null;
    static String dir_path="h:/Extra/db/";
    public static void main(String a[])
    {
            openNlp n=new openNlp();
            StringBuffer s=n.getParsedTree("galaxy is better than iphone 5");
            System.out.println(s);
    }
    static
    {
            try
            {
                    System.out.println("Please wait.....");
                    token_data=new FileInputStream(dir_path+"dataset/openNLP/en-token.zip");
                    tm = new TokenizerModel(token_data);
                    tokenizer =new TokenizerME(tm);
                    pos_data = new FileInputStream(dir_path+"dataset/openNLP/en-pos-maxent.zip");
                    pm = new POSModel(pos_data);
                    sentence_data=new FileInputStream(dir_path+"dataset/openNLP/en-sent.zip");
                    sm=new SentenceModel(sentence_data);

                    parsed_data = new FileInputStream(dir_path+"dataset/openNLP/en-parser-chunking.zip");
                    model = new ParserModel(parsed_data);
                    parser = ParserFactory.create(model);
                     System.out.println("Successfull initiallize the data of opennlp.");
            }
            catch (Exception e)
            {
                    e.printStackTrace();
            }

    }

// This will return the sentence from a Documents.

    public static String[] getSentence(String doc)
    {
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(sm);
            sentence = sentenceDetector.sentDetect(doc);

            return(sentence);
    }

// This will return the words from a sentence

    public static String[] getWords(String statement)
    {
            POSTaggerME tagger = new POSTaggerME(pm);
            tokens = tokenizer.tokenize(statement);
            return tokens;

    }

// This will return the PartOfSpeech Tagging from the given Sentence.

    public static String[] getTokens(String statement)
    {

            try
            {

                    POSTaggerME tagger = new POSTaggerME(pm);
                    tokens = tokenizer.tokenize(statement);
                    pos=tagger.tag(tokens);
                    //System.out.println(pos);
                    for(int i=0;i<pos.length;i++)
                    {
                            if(pos[i].equals("VB") ||pos[i].equals("VBD") ||pos[i].equals("VBG") ||pos[i].equals("VBN") ||pos[i].equals("VBP") ||pos[i].equals("VBZ"))
                                    pos[i]="v";
                            else if(pos[i].equals("WRB"))
                                    pos[i]="r";
                            else if (pos[i].equals("NN") ||pos[i].equals("NNS") ||pos[i].equals("NNP") ||pos[i].equals("NPS"))
                                    pos[i]="n";
                            else if (pos[i].equals("JJ")||pos[i].equals("JJR")||pos[i].equals("JJS"))
                                    pos[i]="a";
                           // else
                                   // pos[i]=null;
                    }
            }
            catch (Exception e)
            {
                    e.printStackTrace();
            }
            finally
            {
                    if (token_data != null)
                    {
                            try {
                                    token_data.close();
                            } catch (IOException e) {
                            }
                            if (pos_data != null) {
                                    try {
                                            pos_data.close();
                                    } catch (IOException e) {
                                    }
                            }
                    }

            }
            return pos;
    }

// This will return StringBuffer parsed in a tree pan Bank format from a given sentence.

    public static StringBuffer getParsedTree(String sentence)
    {
        String parsed_sentence = null;
        StringBuffer sb= new StringBuffer();
        try {

//                parsed_data = new FileInputStream("src/DATA/en-parser-chunking.zip");
//                ParserModel model = new ParserModel(parsed_data);
             parser = ParserFactory.create(model);
           // String t = "The quick brown fox jumps over the lazy dog .";
            opennlp.tools.parser.Parse topParses[] = ParserTool.parseLine(sentence, parser, 1);

            for (opennlp.tools.parser.Parse p: topParses)
            {
              //  p.show();
                p.show(sb);
            }
          //  System.out.println(sb);

        }
        catch (Exception ex)
        {
            Logger.getLogger(openNlp.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
             if (parsed_data != null)
             {
                    try {
                            parsed_data.close();
                        }
                    catch (IOException e) {
             }
        }
    }
    return sb;


}
}
