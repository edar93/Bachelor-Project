angular.module('sdbApp.selecteduser',['ngRoute'])
.factory("selecteduser", function() {
	return {
		user: {},
		setUser : function (u){
			this.user = u;
		},
		getUser : function (){
		return this.user;
		}
	};
})