package  com.example.androidtest.model;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class APIResponse
{
    @SerializedName("title")
    private String title;

    @SerializedName("rows")
    private List<Rows> rows;

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setRows(List<Rows> rows){
        this.rows = rows;
    }
    public List<Rows> getRows(){
        return this.rows;
    }
}