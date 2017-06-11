MedApp.factory("PatientService", function($http) {
 	
 	var URI = "api/patient/";
    var _getAll = function(name) {
    	var config = {}
    	if (name){
    		config.params = {'name': name};
    	}
        return $http.get(URI, config);
    };
     
    var _getByCpf = function(cpf) {
        return $http.get(URI + id);
    };
 
    var _save = function(patient) {
        return $http.post(URI, angular.toJson(patient));
    };
 
    var _update = function(patient) {
        return $http.put(URI + patient.cpf, angular.toJson(patient));
    };
 
    var _delete = function(cpf) {
        return $http.delete(URI + cpf);
    };
 
    return {
        getAll : _getAll,
        getByCpf :_getByCpf,
        save : _save,
        update : _update,
        "delete": _delete,
    };
 
});