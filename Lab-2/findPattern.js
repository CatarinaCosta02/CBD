findPattern = function findPattern(){
    var counter = 0;
    var pattern = "100";

    phone = db.phones.find(); 
    while ( phone.hasNext() ) { 
        var p = phone.next();
        var num = p._id.toString().substring(3,12);
        
        if (num.includes(pattern)){
            counter++;
            print(num);
        }
    }
    print("Number of phone numbers that contains '100': ",counter);
}
