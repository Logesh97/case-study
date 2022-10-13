import { LowerCasePipe } from "@angular/common";
import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
    name : 'wordCapitalize'
})
export class WordCapitalize implements PipeTransform{
    transform(input : string) {
        let wordList:string[] = input.split(' ');
        let output:string = "";
       for(let word of wordList){
           output += word.substring(0,1).toLocaleUpperCase()+word.toLocaleLowerCase().substring(1);
           output += " "
       }
       return output;
    }

}