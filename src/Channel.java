import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Channel {

   private SimpleStringProperty channelName;
   private SimpleStringProperty channelGroup;
   private SimpleStringProperty channelPhoto;
   private SimpleStringProperty channelId;
   private SimpleStringProperty channelLink;
   private SimpleIntegerProperty channelNumber;
   private SimpleStringProperty channelTvgName;


   public Channel(String channelGroup,String channelName,String channelId,String channelPhoto,String channelLink,String channelTvgName,int channelNumber){
       this.channelName = new SimpleStringProperty(channelName);
       this.channelGroup = new SimpleStringProperty(channelGroup);
       this.channelId = new SimpleStringProperty(channelId);
       this.channelPhoto = new SimpleStringProperty(channelPhoto);
       this.channelLink = new SimpleStringProperty(channelLink);
       this.channelNumber = new SimpleIntegerProperty(channelNumber);
       this.channelTvgName = new SimpleStringProperty(channelTvgName);
   }

   public Channel(){

   }

   public String getChannelId(){
       return this.channelId.get();
   }

   public void setChannelName(String channelName){
       this.channelName.set(channelName);
   }

   public String getChannelName(){
       return this.channelName.get();
   }

   public void setChannelTvgName(String channelTVGName){
       this.channelTvgName.set(channelTVGName);
   }

   public String getChannelTvgName(){
       return this.channelTvgName.get();
   }

   public String getChannelPhoto(){
       return this.channelPhoto.get();
   }

   public void setChannelPhoto(String channelPhoto){
       this.channelPhoto.set(channelPhoto);
   }

   public void setChannelNumber(int number){
       this.channelNumber.set(number);
   }

   public int getChannelNumber(){
       return this.channelNumber.get();
   }

   public String getChannelLink(){
       return this.channelLink.get();
   }

   public void setChannelLink(String link){this.channelLink.set(link);}

   public String getChannelGroup(){
       return this.channelGroup.get();
   }

   public void setChannelGroup(String channelGroup){
       this.channelGroup.set(channelGroup);
   }



}
