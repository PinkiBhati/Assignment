
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
