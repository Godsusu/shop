var reduce1=document.getElementById("r1");
var numbers1=document.getElementById("n1");
var plus1=document.getElementById("p1");
var money1=document.getElementById("w1");
var summobey1=document.getElementById("d1");

var reduce2=document.getElementById("r2");
var numbers2=document.getElementById("n2");
var plus2=document.getElementById("p2");
var money2=document.getElementById("w2");
var summobey2=document.getElementById("d2");

var reduce3=document.getElementById("r3");
var numbers3=document.getElementById("n3");
var plus3=document.getElementById("p3");
var money3=document.getElementById("w3");
var summobey3=document.getElementById("d3");

var reduce4=document.getElementById("r4");
var numbers4=document.getElementById("n4");
var plus4=document.getElementById("p4");
var money4=document.getElementById("w4");
var summobey4=document.getElementById("d4");

var CheckBox1=document.getElementById("Bb1");
var CheckBox2=document.getElementById("Bb2");

var Checkbox1=document.getElementById("B1");
var checkbox1=document.getElementById("b1");
var Checkbox2=document.getElementById("B2");
var checkbox2=document.getElementById("b2");
var Checkbox3=document.getElementById("B3");
var checkbox3=document.getElementById("b3");
var Checkbox4=document.getElementById("B4");
var checkbox4=document.getElementById("b4");


checkbox1.onclick=function(){
    if(checkbox1.checked!=true){
       Checkbox1.checked=false;
       CheckBox2.checked=false;
       CheckBox1.checked=false;
    }
}
checkbox2.onclick=function(){
    if(checkbox2.checked!=true){
       Checkbox2.checked=false;
       CheckBox2.checked=false;
       CheckBox1.checked=false;
    }
}
checkbox3.onclick=function(){
    if(checkbox3.checked!=true){
       Checkbox3.checked=false;
       CheckBox2.checked=false;
       CheckBox1.checked=false;
    }
}
checkbox4.onclick=function(){
    if(checkbox4.checked!=true){
       Checkbox4.checked=false;
       CheckBox2.checked=false;
       CheckBox1.checked=false;
    }
}


Checkbox1.onclick=function(){
    if(Checkbox1.checked==true){
        checkbox1.checked=true;
    }else{
        checkbox1.checked=false;
        CheckBox2.checked=false;
        CheckBox1.checked=false;
    }
}
Checkbox2.onclick=function(){
    if(Checkbox2.checked==true){
        checkbox2.checked=true;
    }else{
        checkbox2.checked=false;
        CheckBox2.checked=false;
        CheckBox1.checked=false;
    }
}
Checkbox3.onclick=function(){
    if(Checkbox3.checked==true){
        checkbox3.checked=true;
    }else{
        checkbox3.checked=false;
        CheckBox2.checked=false;
        CheckBox1.checked=false;
    }
}
Checkbox4.onclick=function(){
    if(Checkbox4.checked==true){
        checkbox4.checked=true;
    }else{
        checkbox4.checked=false;
        CheckBox2.checked=false;
        CheckBox1.checked=false;
    }
}

CheckBox1.onclick=function(){
    if(CheckBox1.checked==true){
        Checkbox1.checked=true;
        checkbox1.checked=true;
        Checkbox2.checked=true;
        checkbox2.checked=true;
        Checkbox3.checked=true;
        checkbox3.checked=true;
        Checkbox4.checked=true;
        checkbox4.checked=true;
        CheckBox2.checked=true;
    }else{
        Checkbox1.checked=false;
        checkbox1.checked=false;
        Checkbox2.checked=false;
        checkbox2.checked=false;
        Checkbox3.checked=false;
        checkbox3.checked=false;
        Checkbox4.checked=false;
        checkbox4.checked=false;
        CheckBox2.checked=false;
    }
}
CheckBox2.onclick=function(){
    if(CheckBox2.checked==true){
        Checkbox1.checked=true;
        checkbox1.checked=true;
        Checkbox2.checked=true;
        checkbox2.checked=true;
        Checkbox3.checked=true;
        checkbox3.checked=true;
        Checkbox4.checked=true;
        checkbox4.checked=true;
        CheckBox1.checked=true;
    }else{
        Checkbox1.checked=false;
        checkbox1.checked=false;
        Checkbox2.checked=false;
        checkbox2.checked=false;
        Checkbox3.checked=false;
        checkbox3.checked=false;
        Checkbox4.checked=false;
        checkbox4.checked=false;
        CheckBox1.checked=false;
    }
}


plus1.onclick=function(){
    addtion(numbers1,money1,"n1","d1");
}

reduce1.onclick=function(){
    subduction(numbers1,money1,"n1","d1");
}

plus2.onclick=function(){
    addtion(numbers2,money2,"n2","d2");
}

reduce2.onclick=function(){
    subduction(numbers2,money2,"n2","d2");
}

plus3.onclick=function(){
    addtion(numbers3,money3,"n3","d3");
}

reduce3.onclick=function(){
    subduction(numbers3,money3,"n3","d3");
}

plus4.onclick=function(){
    addtion(numbers4,money4,"n4","d4");
}

reduce4.onclick=function(){
    subduction(numbers4,money4,"n4","d4");
}



function  addtion(num,mon,nunname,subname) 
{  
    a=parseInt(num.value)+1;
    str=mon.innerText;
    str1=str.substring(1,str.length);
    b=parseFloat(str1);
    c=(a*b).toFixed(2);
    document.getElementById(nunname).value=a;
    document.getElementById(subname).innerText=c;
}
function subduction(num,mon,nunname,subname) {
    a=parseInt(num.value)-1;
    if(a>=0){
    str=mon.innerText;
    str1=str.substring(1,str.length);
    b=parseFloat(str1);
    c=(a*b).toFixed(2);
    document.getElementById(nunname).value=a;
    document.getElementById(subname).innerText=c;
    }
}
window.onclick=function(){
    var allsum=0;
    var count=0;
    if(checkbox1.checked==true){
        str=summobey1.innerText;
        str1=parseFloat(str);
        allsum=parseFloat(allsum)+str1;
        count++;
    }
    if(checkbox2.checked==true){
        str=summobey2.innerText;
        str1=parseFloat(str);
        allsum=parseFloat(allsum)+str1;
        count++;
    }
    if(checkbox3.checked==true){
        str=summobey3.innerText;
        str1=parseFloat(str);
        allsum=parseFloat(allsum)+str1;
        count++;
    }
    if(checkbox4.checked==true){
        str=summobey4.innerText;
        str1=parseFloat(str);
        allsum=parseFloat(allsum)+str1;
        count++;
    }
    document.getElementById("count").innerText=count;
    document.getElementById("total").innerText=allsum.toFixed(2);
}