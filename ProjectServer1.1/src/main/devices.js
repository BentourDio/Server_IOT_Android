class IOT{
    

    constructor(id,smoke,gas,temp,uv,x,y,b,map){
        this.id=id;
        this.smoke =smoke;
        this.gas = gas;
        this.temp = temp;
        this.uv=uv;
        this.x=x;
        this.y=y;
        this.battery=b;
        this.circle;
        
        this.marker= new google.maps.Marker({
            position: {lat: x, lng: y},
            title: id,
            draggable: true,
            map,
            icon: "http://maps.google.com/mapfiles/ms/icons/blue.png"
            
           //"http://maps.google.com/mapfiles/ms/icons/orange-dot.png"
            // "http://maps.google.com/mapfiles/ms/icons/caution.png"
            
        
        });
        
        this.infowindow= new google.maps.InfoWindow({
            content: "<p>"+"id:" + id + "<br />" + "gas:"+gas+"\n"+"<br />" + 
            "smoke:"+smoke+"<br />" + "\n"+"temp:"+temp+"<br />" +"UV:"+uv+"<br />" + 
            "battery:"+b +"</p>"
        });
        this.marker.addListener("click", ()=>{
            this.infowindow.open({
                anchor: this.marker,
                map,
                shouldfocus: true
            });
        });

    }
     updateValuesInfoW(smoke,gas,temp,uv,x,y) {
        this.smoke =smoke;
        this.gas = gas;
        this.temp = temp;
        this.uv=uv;
        if (this.x != x || this.y != y){
            this.x = x;  this.y = y;
            this.marker.setPosition(new google.maps.LatLng(x,y));    
        }
        this.infowindow.setContent("<p>"+"id:" + this.id + "<br />" + "gas:"+gas+"\n"+"<br />" + 
        "smoke:"+smoke+"<br />" + "\n"+"temp:"+temp+"<br />" +"UV:"+uv+"<br />" + 
        "x:"+x + "<br />"+ "y:"+y+"</p>");
    }

}

class Android{
    constructor(id,x,y,b,map){
        this.x=x;
        this.y=y;
        this.id=id;
        this.battery=b;
        this.marker=new google.maps.Marker({
            position: {lat: x, lng: y},
            title: id,
            draggable: false,
            map,

        })
        this.infowindow= new google.maps.InfoWindow({
            content:"<p>" + "id:"+this.id+"<br /> " +"battery:" +this.battery+"<br />"
        });
        this.marker.addListener("click", () =>{
            this.infowindow.open({
                anchor: this.marker,
                map,
                shouldfocus:true
            });
        });
        

    }

    updateValuesAndroid(x,y,b){
        this.x=x;
        this.y=y;
        this.battery=b;
        this.infowindow.setContent("<p>" + "id:"+this.id+"<br /> " +"battery:" +this.battery+"<br />");
        this.marker.setPosition(new google.maps.LatLng(x,y));
    }
}
