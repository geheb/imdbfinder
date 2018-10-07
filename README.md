# imdbfinder [![Build status](https://ci.appveyor.com/api/projects/status/cux8wvg4r2okag5p?svg=true)](https://ci.appveyor.com/project/gethomast/imdbfinder)
find any movie on IMDb and extract meta information 

## Prerequisites
* Java 10 compatible OS
* [Java 10 Runtime](https://www.oracle.com/technetwork/java/javase/downloads/index.html)

## Download
Latest [Package](https://github.com/geheb/imdbfinder/releases/latest)

## Usage
```
imdbfinder [-hv] [-c=<contentType>] [-o=<outputFile>] -t=<movieTitle>

  -c, --contentType=<contentType>
                             Content type: KeyValue, Json. Default: KeyValue
  -h, --help                 display this help
  -o, --output=<outputFile>  Sets the output file. Default: stdout
  -t, --title=<movieTitle>   title of the movie to find
  -v, --version              display version info
```

### Example

Find "X-Men"
	
	java -jar imdbfinder-1.0-jar-with-dependencies.jar -t x-men

The output will be key/value like

	movieUrl=https://...
	title=X-Men
	datePublished=2000-07-13
	userRating=7.4
	genre=Action, Adventure, Sci-Fi
	contentRating=PG-13
	keywords=mutant, professor, superhero, superhero team, first part
	imageUrl=https://...
	duration=PT1H44M
	description=Two mutants come to a private academy for their kind whose resident superhero team must oppose a terrorist organization with similar powers.

## License
[MIT](https://github.com/geheb/imdbfinder/blob/master/LICENSE)
