const jsonServer = require('json-server')
const server = jsonServer.create()
const router = jsonServer.router('commands.json')
const middlewares = jsonServer.defaults()
var express = require ("express");
var app = express();
app.use(express.static("./public/"));
server.use(middlewares)
server.use(router)
var request = require ('request');
var url = "http://localhost:3000/Command"


app.get('/',function(req,res){
	request(url, function(error,response,body){
		if(!error && response.statusCode==200){
			var parse= JSON.parse(body);
			res.render("home.ejs", {parse: parse});
			console.log(parse["Type"]);
		}
	});
});

app.get('/commands',function(req,res){
	res.send("commands.json");
});
app.listen(9000,process.env.IP, function(){
	console.log("It's Alive!");
});
server.listen(3000, () => {
  console.log('JSON Server is running')
})