package com.kinview.util;

public class SystemSetting
{
//  private String login_url="";  //登录地址
//  private String mail_url="";   //接收邮件地址
//  private String pushmail_url="";    //pushmail地址
//  private String serverip="";   //记录服务器地址
  private String id="";  //记录id
  private String Description=""; //路url的描述
  private String url=""; //记录url的内容
  
  public String getId()
  {
    return id;
  }
  public void setId(String id)
  {
    this.id = id;
  }
  public String getDescription()
  {
    return Description;
  }
  public void setDescription(String description)
  {
    Description = description;
  }
  public String getUrl()
  {
    return url;
  }
  public void setUrl(String url)
  {
    this.url = url;
  }
  
 
  
}
