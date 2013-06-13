/**
 * Created by fukudaatsushi on 13/06/13.
 */
///<reference path='../libs/DefinitelyTyped/angularjs/angular.d.ts' />

///<reference path='../Model.ts' />
module TodoController {
    export interface Scope extends ng.IScope {
        todos: Model.Todo[];

        add : () => void;
        newContent:string;

    }

    export class Controller {
        constructor(public $scope:Scope){
            this.$scope.todos = [
                new Model.Todo("ss1"),
                new Model.Todo("ss2")

            ];
            this.$scope.add = () => this.add();

        }

        public add(){
            var content:string = this.$scope.newContent;
            var todo:Model.Todo = new Model.Todo(content);
            this.$scope.todos.push(todo);
        }

    }
}
