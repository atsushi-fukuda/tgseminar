/**
 * Created by fukudaatsushi on 13/06/13.
 */
///<reference path='../libs/DefinitelyTyped/angularjs/angular.d.ts' />

///<reference path='../Model.ts' />
///<reference path='../Service.ts' />


module TodoController {
    export interface Scope extends ng.IScope {
        todos: Model.Todo[];

        add : () => void;
        newContent:string;
        //modContent:string;

        remove:(index:number)=>void;
        modify:(index:number)=>void;
        getList:()=>void;

        debugIndex:number;

    }

    export class Controller {
        constructor(public $scope:Scope,public $window:Window,public todoService:Service.TodoService){
            this.$scope.todos = [
                new Model.Todo("ss1"),
                new Model.Todo("ss2")

            ];
            this.$scope.add = () => this.add();
            this.$scope.remove = (index:number) => this.remove(index);
            /*
            this.$scope.remove = function(index:number):void =>{
                this.$scope.debugIndex = index;
                this.$scope.todos.splice(index,1);
            }
            */

            this.$scope.modify = (index:number) => this.modify(index);
            /*
            this.$scope.modify = function(index:number):void =>{
                //var targetContent = this.$scope.oldContents[index];
                //this.$scope.todos[index].content = targetContent;
            }
            */
            this.todoService.getList()
                .success(
                    (todos:Model.Todo[])=>
                    {
                        this.$scope.todos = todos;
                    }
                );

        }

        add(){
            var content:string = this.$scope.newContent;
            var todo:Model.Todo = new Model.Todo(content);
            this.$scope.todos.push(todo);
        }
        remove(index:number){
            //this.$scope.debugIndex = this.$scope.debugIndex;
            this.$scope.todos.splice(index,1);
        }
        modify(index:number){
            //this.$scope.modContent = this.$window.prompt("e");
            var modContent = this.$window.prompt("change to ?",this.$scope.todos[index].title);
            //console.log("sss" + modContent);
            if(modContent){
                this.$scope.todos[index].title = modContent;
            }
        }
    }
}
