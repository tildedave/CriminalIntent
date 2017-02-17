package database

object CrimeDbSchema {
    object CrimeTable {
        var NAME = "crimes";

        object Cols {
            var UUID   = "uuid";
            var TITLE  = "title";
            var DATE   = "date";
            var SOLVED = "solved";
        }
    }
}