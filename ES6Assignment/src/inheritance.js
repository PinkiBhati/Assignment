class Car{
    constructor(name)
    {
        this.name=name;
    
    }

    static getName()
    {
        return `This is  car`;
    }
}


class Model extends Car{
    constructor(name, model)
    {
        super(name);
        this.model=model;

    }

    static getModel()
    {
        return `this is  model` ;
    }
}

class Year extends Model{
    constructor(name,model,year)
    {
        super(name,model);
        this.year=year;
    }

    static getYear()
    {
        return `Made in  year`;
    }
}



export {Car,Model,Year};