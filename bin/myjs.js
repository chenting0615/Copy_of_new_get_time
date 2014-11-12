var xmlhttp=new ActiveXObject("MSXML2.XMLHTTP.3.0");
xmlhttp.open("GET","远程服务器地址",false);
xmlhttp.setRequestHeader("If-Modified-Since","q");
xmlhttp.send();
var dateStr= xmlhttp.getResponseHeader("Date");
//alert(dateStr);
var d = new Date(dateStr);
document.write(d);