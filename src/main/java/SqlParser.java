import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tf on 2016/10/30.
 */
public class SqlParser {
    public void getSqlInfo(String sql) throws Exception {
        String begintime = "";
        String endtime = "";

        String lowsql = sql.toLowerCase();
        Pattern patternwhere = Pattern.compile("where.*$");
        Matcher matcherwhere = patternwhere.matcher(sql);
        //String strAfterwhere = "";
        if(!matcherwhere.find()){
            throw new Exception("find no valid sql");
        }
        String strAfterwhere = matcherwhere.group().replaceAll("where\\s","").trim();
        String[] conditions = strAfterwhere.split("and");
        for(String condition:conditions){
            if(condition.contains("messagedate")){
                if(condition.contains(">")){
                    Pattern patternbegin = Pattern.compile("'(.*)'");
                    Matcher matcherbegin = patternbegin.matcher(condition);
                    if(matcherbegin.find()){
                        begintime = matcherbegin.group().replaceAll("\'","");
                    }
                }

                if(condition.contains("<")){
                    Pattern patternend = Pattern.compile("\'(.*)\'");
                    Matcher matcherend = patternend.matcher(condition);
                    if(matcherend.find()){
                        endtime = matcherend.group().replaceAll("\'","");
                    }
                }
            }
        }

        System.out.println("begintime:"+begintime+" endtime:"+endtime);
    }

    public static void main(String[] args) throws Exception {
        SqlParser sqlParser = new SqlParser();
        sqlParser.getSqlInfo("select * from test where      messagedate<'2016-08' and messagedate>'240-09'");
    }
}
