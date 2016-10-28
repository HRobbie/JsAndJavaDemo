var first=true;
function loadingAgain(){
	try{
		if(first){		
			javascript:Android.loadingAgain();
			first=false;
		}
	}catch(e){
		//TODO handle the exception
		console.log(e.message);
	}
	
	console.log("调用了重新加载的方法");

}

