# imdbfinder
Find any movie on IMDb and show basic meta information 

## Prerequisites
* https://adoptium.net/de/temurin/releases/

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
	
	java -jar ./imdbfinder-1.2.0-jar-with-dependencies.jar -t x-men

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

