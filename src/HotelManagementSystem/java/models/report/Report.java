package models.report;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Report")
public class Report {
    @DatabaseField(generatedId = true )
    int id;
    @DatabaseField(canBeNull = false)
    String Subject;
    @DatabaseField(canBeNull = false)
    String Content;

    public Report(String Subject , String Content){
        this.Subject = Subject;
        this.Content = Content;
    }
    public Report(){

    }
}
