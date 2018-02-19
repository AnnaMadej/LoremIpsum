package com.aniamadej.loremipsum.Models;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Random;

@AllArgsConstructor
public enum Words {

    LOREM_IPSUM("lorem", "Lorem ipsum dolor sit amet", new String[]{
            "lorem", "ipsum", "dolor", "sit", "amet", "consectetur",
            "adipiscing", "elit", "curabitur", "vel", "hendrerit", "libero",
            "eleifend", "blandit", "nunc", "ornare", "odio", "ut",
            "orci", "gravida", "imperdiet", "nullam", "purus", "lacinia",
            "a", "pretium", "quis", "congue", "praesent", "sagittis",
            "laoreet", "auctor", "mauris", "non", "velit", "eros",
            "dictum", "proin", "accumsan", "sapien", "nec", "massa",
            "volutpat", "venenatis", "sed", "eu", "molestie", "lacus",
            "quisque", "porttitor", "ligula", "dui", "mollis", "tempus",
            "at", "magna", "vestibulum", "turpis", "ac", "diam",
            "tincidunt", "id", "condimentum", "enim", "sodales", "in",
            "hac", "habitasse", "platea", "dictumst", "aenean", "neque",
            "fusce", "augue", "leo", "eget", "semper", "mattis",
            "tortor", "scelerisque", "nulla", "interdum", "tellus", "malesuada",
            "rhoncus", "porta", "sem", "aliquet", "et", "nam",
            "suspendisse", "potenti", "vivamus", "luctus", "fringilla", "erat",
            "donec", "justo", "vehicula", "ultricies", "varius", "ante",
            "primis", "faucibus", "ultrices", "posuere", "cubilia", "curae",
            "etiam", "cursus", "aliquam", "quam", "dapibus", "nisl",
            "feugiat", "egestas", "class", "aptent", "taciti", "sociosqu",
            "ad", "litora", "torquent", "per", "conubia", "nostra",
            "inceptos", "himenaeos", "phasellus", "nibh", "pulvinar", "vitae",
            "urna", "iaculis", "lobortis", "nisi", "viverra", "arcu",
            "morbi", "pellentesque", "metus", "commodo", "ut", "facilisis",
            "felis", "tristique", "ullamcorper", "placerat", "aenean", "convallis",
            "sollicitudin", "integer", "rutrum", "duis", "est", "etiam",
            "bibendum", "donec", "pharetra", "vulputate", "maecenas", "mi",
            "fermentum", "consequat", "suscipit", "aliquam", "habitant", "senectus",
            "netus", "fames", "quisque", "euismod", "curabitur", "lectus",
            "elementum", "tempor", "risus", "cras"
    }),

    GAME_OF_THRONES("got", "Winter is comming", new String[]
            {       "eddard", "ned", "stark", "robert", "baratheon", "jaime", "lannister",
                    "catelyn", "cersei", "daenerys", "targaryen", "jorah", "mormont", "petyr",
                    "baelish", "viserys", "jon", "snow", "sansa", "arya", "robb", "theon",
                    "greyjoy", "bran", "joffrey", "sandor", "clegane", "tyrion", "khal",
                    "drogo", "tywim", "davos", "seaworth", "samwell", "tarly", "margaery",
                    "tyrell", "stannis", "melisandre", "jeor", "bronn", "varys", "shae",
                    "ygritte", "talisa", "maegyr", "gendry", "tormund", "giantsbane", "gilly",
                    "brienne", "ramsay", "bolton", "ellaria", "sand", "daario", "naharis",
                    "missandei", "jaqen", "h'ghar", "tommen", "roose", "sparrow", "pycelle",
                    "meryn", "HODOR", "grenn", "osha", "rickon", "ros", "gregor", "janos",
                    "slynt", "lancel", "myrcella", "rodrik", "cassel", "luwim", "irri",
                    "doreah", "kevan", "barristan", "selmy", "rast", "aemon", "pypar",
                    "alliser", "thorne", "othell", "yarwyck", "loras", "beric", "dondarrion",
                    "podrick", "payne", "eddison", "tollett", "yara", "selyse", "florent", "sam",
                    "worm", "ollena", "shireen", "meera", "reed", "jojen", "thoros", "olly", "mace",
                    "waif", "bowen", "marrsh"
            });

    private String name;
    @Getter
    private String startingPhrase;
    @Getter
    private String[] words;

    public static Words getTextFromName(String name) {
        for (Words words : Words.values()) {
            if (words.name.equals(name)) {
                return words;
            }
        }
        return Words.LOREM_IPSUM;
    }

    public String getRandomWord(){
        Random random = new Random();
        return getWords()[random.nextInt(getWords().length)];
    }
}
