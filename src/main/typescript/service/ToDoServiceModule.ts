///<reference path='../libs/DefinitelyTyped/angularjs/angular.d.ts' />

///<reference path='../Model.ts' />

'use strict';

module Service {

    export class TodoService {

        constructor(public $http:ng.IHttpService) {
        }

        getList():ng.IHttpPromise {
            var promise = this.$http.get("/list");
            var wrapped:ng.IHttpPromise = {
                success: (callback) => {
                    promise.success((data, status, headers, config) => {
                        var resultList:Model.Todo[] = [];
                        data.forEach((data)=> {
                            resultList.push(new Model.Todo(data));
                        });
                        callback(resultList, status, headers, config);
                    });
                },
                error: promise.error,
                then: promise.then
            };
            return wrapped;
        }

        add(title):ng.IHttpPromise {
            var data = {"title":title};
            var transform = function(data){
                return $.param(data);
            }
            var promise = this.$http.post(
                "/Post",
                data,
                //{"params":data}
                {
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
                    transformRequest: transform
                }
            );
            var wrapped:ng.IHttpPromise = {
                success: (callback) => {
                    promise.success((data, status, headers, config) => {
                        callback(data, status, headers, config);
                    });
                },
                error: promise.error,
                then: promise.then
            };
            return wrapped;
        }

        remove(id):ng.IHttpPromise {
            var data = {"id":id};
            var transform = function(data){
                return $.param(data);
            }
            var promise = this.$http.post(
                "/Delete",
                data,
                //{"params":data}
                {
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
                    transformRequest: transform
                }
            );
            var wrapped:ng.IHttpPromise = {
                success: (callback) => {
                    promise.success((data, status, headers, config) => {
                        callback(data, status, headers, config);
                    });
                },
                error: promise.error,
                then: promise.then
            };
            return wrapped;
        }
        modify(id,title):ng.IHttpPromise {
            var data = {"id":id,"title":title};
            var transform = function(data){
                return $.param(data);
            }
            var promise = this.$http.post(
                "/Update",
                data,
                //{"params":data}
                {
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
                    transformRequest: transform
                }
            );
            var wrapped:ng.IHttpPromise = {
                success: (callback) => {
                    promise.success((data, status, headers, config) => {
                        callback(data, status, headers, config);
                    });
                },
                error: promise.error,
                then: promise.then
            };
            return wrapped;
        }    }
}
