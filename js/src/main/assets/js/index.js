/**
外层不能是$(fucntion(){})
**/


function javaCallJs(arg){
	$('#content').html("<br/>"+arg);
}


function showHtmlcallJava(){
	var str=window.Android.returnSomething("我");
	alert(str);
}

function javaTojava(arg){
    $('#content').html("<br/>"+arg);
    var str1="234";
    javascript:Android.toastSomething(str1);
}

function stop(arg){
    $('#stop').html("<br/>"+arg);
}