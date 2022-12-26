//@author hcadavid

apimock=(function(){

	var mockdata=[];

	mockdata["yesid"]=	[{author:"yesid","points":[{"x":150,"y":120},{"x":215,"y":115},{"x":34,"y":98}],"name":"house"},
	 {author:"yesid","points":[{"x":340,"y":240},{"x":15,"y":215}],"name":"gear"}];
	mockdata["david"]=[{author:"david","points":[{"x":140,"y":140},{"x":115,"y":115}],"name":"house2"},
	 {author:"david","points":[{"x":140,"y":140},{"x":115,"y":115}],"name":"gear2"}];
	mockdata["Cristianc"]=[{author:"Cristianc","points":[{"x":65,"y":45},{"x":89,"y":43}],"name":"prueba"},
	{author:"Cristianc","points":[{"x":76,"y":23},{"x":56,"y":9}],"name":"prueba2"}];


	return {
		getBlueprintsByAuthor:function(authname,callback){
			callback(
				mockdata[authname]
			);
		},

		getBlueprintsByNameAndAuthor:function(authname,bpname,callback){

			callback(
				mockdata[authname].find(function(e){return e.name===bpname})
			);

		},
		updateBlueprint:function(list,callback,newPoint,ID){
			callback(list,newPoint,ID);
		}

	};	

})();

/*
Example of use:
var fun=function(list){
	console.info(list);
}

apimock.getBlueprintsByAuthor("johnconnor",fun);
apimock.getBlueprintsByNameAndAuthor("johnconnor","house",fun);*/