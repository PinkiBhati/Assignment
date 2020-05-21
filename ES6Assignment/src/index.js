import {Car,Model,Year} from './inheritance.js'
import {areaOfCircle,areaOfRect,areaOfCylinder} from './area.js'
import {uniqueElements} from './unique.js'
import {stackUsingLL} from './stack.js'
import  {Node,LinkedList} from './linkedList.js'

//Que 1 Given this array: `[3,62,234,7,23,74,23,76,92]`, Using arrow function,
// create an array of the numbers greater than `70`.
const filterSet = [3, 62, 234, 7, 23, 74, 23, 76, 92];
const filtered2 = filterSet.filter(number => number > 70); 
console.log(filtered2);

//Que2(localhost:3000/Que2.html)

//Que 3 

const song = {
name: 'Dying to live',
artist: 'Tupac',
featuring: 'Biggie Smalls'
};

console.log(`<div class="song">
<p>
${song.name} — ${song.artist} \n(${song.featuring})
</p>
</div>`)


console.log("4.Extract all keys inside address object from user object using destructuring ")
const user = {
    firstName: 'Sahil',
    lastName: 'Dua',
    Address: {
    Line1: 'address line 1',
    Line2: 'address line 2',
    State: 'Delhi',
    Pin: 110085,
    Country: 'India',
    City: 'New Delhi',
    },
    phoneNo: 9999999999
    };

let{Line1, Line2,State,Pin,Country,City}= user.Address;
console.log(Line1,Line2,State,Pin,Country,City); 

console.log("4. Filter unique array members using Set.");   

let arrayOfduplicates= [50,50,40,40,30,30,20,20,10,10];
let uniqueEle= new Set(arrayOfduplicates);
console.log(uniqueEle);

console.log("5. Find the possible combinations of a string and store them in a MAP ")

function allCombination(string) {
    function combinations(i, temp) {
        if (i >= string.length) {
            if(temp=='')
            {
             return;
            }
            
            result.add(temp);
            return;
        }
        combinations(i + 1, temp + string[i]);
        combinations(i + 1, temp);
    }

    var result = new Set();
    combinations(0,'');
    return result;
}
console.log(allCombination("car"));

console.log("6 Write a program to implement inheritance upto 3 classes.The Class must  public variables and static functions.");

let obj= new Year("BMW","Series Sedan",2016);
console.log(obj);
console.log(Car.getName());
console.log(Model.getModel());
console.log(Year.getYear());

console.log("7. Write a program to implement a class having static functions")

class Car1{
    constructor(name)
    {
        this.name=name;
    
    }

    static getName()
    {
        return `This is  car`;
    }
}

let obj1= new Car1("BMW");
console.log(Car1.getName());

console.log("8. Import a module containing the constants and method for calculating area of circle, rectangle, cylinder.")
console.log(areaOfCircle(5));
console.log(areaOfCylinder(9,7));
console.log(areaOfRect(4,5));

console.log("9 Import a module for filtering unique elements in an array.")

console.log(uniqueElements);

console.log("10 . Write a program to flatten a nested array to single level using arrow functions.")

let nonFlatenedArray= [[1,[2]],[3,4,[7]],[8,[9,10,11,[3,5,6]]]];
console.log(nonFlatenedArray.flat(Infinity));

console.log("11. Implement a singly linked list in es6 and implement addFirst() addLast(), length(), getFirst(), getLast(). (without using array)")

let ll= new LinkedList();
ll.addFirst(67);
ll.addFirst(90);
ll.addLast(89);
console.log(ll);
console.log(ll.length());
console.log(ll.getFirst());
console.log(ll.getLast());
console.log("hello");
ll.print();

console.log("12. Implement Map and Set using Es6");
console.log("Map");

 let string= "pinki bhati ";

 let letters= new Map();

 for(let i=0;i<string.length;i++)
 {
     let letter = string[i];

     if(!letters.has(letter))
     {
         letters.set(letter,1);
     }
     else{
         letters.set(letter, letters.get(letter)+1);
     }
 }

 console.log(letters);

 console.log("Set");

 let array= [70,86,90,56,70,86];
 let newSet= new Set(array);
 newSet.add(89);
 newSet.delete(86);
 console.log(newSet);

console.log("13.  Implementation of stack (using linked list)");

let stack = new stackUsingLL();

stack.push(56);
stack.push(78);
stack.pop();
stack.push(80);

console.log("Elememts in stack are ");
console.log( stack.toArray());
console.log(stack.isEmpty());
console.log("size of stack is  " + stack.size());
console.log(stack.peek());
