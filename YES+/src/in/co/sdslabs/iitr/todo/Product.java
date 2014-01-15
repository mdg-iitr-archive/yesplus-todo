package in.co.sdslabs.iitr.todo;

public class Product {
	 String task;
     String date;
     int num;
     boolean box;
     String time;
     int id;
     int done;
     
     Product(String _describe, int _num, String _date,String _time, boolean _box,int _id,int _done) {
       task = _describe;
       date = _date;
       num = _num;
       box = _box;
       time=_time;
       id=_id;
       done=_done;
     }
}
