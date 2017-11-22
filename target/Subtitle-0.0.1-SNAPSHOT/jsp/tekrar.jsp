<%-- 
    Document   : ogren
    Created on : 15.Tem.2017, 05:04:32
    Author     : numan
--%>

<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>Tekrar</title>
</head>
<body>

<div class="container-fluid text-center" style="width: 50%;margin-top:5%;panding-bottom:5%"><!--// bg-success-->
    <h1 style="text-align: left;color: aliceblue">inkişişce :</h1>
        <h2 id="soru" style="color: aliceblue">yazilar</h2> <br />
</div>
<div class="container-fluid text-center" style="width: 50%;"><!--// bg-primary -->
        <h1 style="text-align: left;color: aliceblue">Turkçe : </h1>
        <h2 id="cevap" style="visibility: visible;color: aliceblue">yazilar</h2> <br /><br />
        <!--<p1 id="x" style="visibility: hidden"></p1> <br /><br />-->
        <!--<input type="text" name="anw" id="anw" style="visibility: hidden" value=""></input>-->
        
        <button type="button" class="btn btn-default btn-lg pull-right" id="bas">İlerle</button>
</div>


 
        
</body>
<script>
var cnt=0;
var cnt2=0;
var myObject = new Object();
var snc = {};
var answer="";

function cevapsiz(){
    var degerler = "do=yKelimeTekrar";
    
    $.post("../session/getRepeatQuestion",degerler,function(data,state)
    {
        var obj = jQuery.parseJSON(JSON.stringify(data));
        if (obj.kelimeTekrar==null)
        {
            $.post("../session/saveRepeatedSession",{do:"kaydet",snc:JSON.stringify(snc)},function(data,state)
            {
               var gelen=$.trim(data);
               alert(gelen);
               location.href="../";
            });
            //location.href = "home.jsp";
        }
        else
        {
            $("#soru").text(obj.kelimeTekrar.soru);
            $("#x").text(obj.kelimeTekrar.cevap);
            myObject.cvp=obj.kelimeTekrar.cevap;
            myObject.id=obj.kelimeTekrar.id;
            myObject.siklar=obj.siklar;
            $("#cevap").html("<div class='list-group' >"+
                              "<a href='#' id='1' class='list-group-item'>"+myObject.siklar[3]+"</a>"+
                              "<a href='#' id='2' class='list-group-item'>"+myObject.siklar[0]+"</a>"+
                              "<a href='#' id='3' class='list-group-item'>"+myObject.siklar[1]+"</a>"+
                              "<a href='#' id='1' class='list-group-item'>"+myObject.siklar[2]+"</a>"+
                            "</div>");
            console.log("iki"+obj.kelimeTekrar.kelime_tipi+": -"+myObject.cvp+"---"+obj.siklarList);
            cnt2=cnt2+1;
            $("#bas").off('click').on('click', kontrol);
            $(".list-group-item").click(function(){
                        $(myObject.element).css('background', '');
                        $(this).css('background', '#0099ff');
                myObject.reply = $(this).text();
                myObject.element=$(this);
            });
        }
        
    });
};
function kontrol()
{
    console.log("correct : "+ myObject.cvp + "reply : "+myObject.reply)
    var karar = "1";
    $(myObject.element).css('background', '#33ff33');
    if (myObject.reply!=myObject.cvp){karar="0";$(myObject.element).css('background', '#ff3333');$('.list-group-item:contains('+myObject.cvp+')').css('background', '#00ff99');};
    if (snc[myObject.id]){snc[myObject.id].push(karar)}//ilk sorulmuyorsa defa soruluyorsa
    else {snc[myObject.id]=[karar];}//soru ilk defa cevaplandiysa kelime id si icin list acar.
    //console.log("Answer : "+answer+" Dogru : "+dogru+myObject.id);
    console.log(JSON.stringify(snc));//json object i sitring e cevirir.
    if (karar==1)
    {
        setTimeout(function(){
                cevapsiz();
    }, 250);
    }
    else
    {
        $("#bas").off('click').on('click', devam);
    }
}
function devam(){
                cevapsiz();
}
function codeAddress() {
    $.post("../session/repeat",{ do: "tekrarKelimeUret"},function(data,state)
    {
        var gelen = $.trim(data);
        alert(gelen);
        cevapsiz();
        
    });
    
}

window.onload = codeAddress;
</script>
</html>
