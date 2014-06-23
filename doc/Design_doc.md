DESIGN DOCUMENT
===============

This is the design document for **Dreamteam - Eredivisie**.

Databases:
----------

**Players Database**

| ID (unique)    | NAME            | CLUB            | POSITION       | SIDE                 | MARKET VALUE            |
| -------------- | --------------- | --------------- | -------------- | -------------------- | ----------------------- |
| *generated ID* | *player's name* | *player's club* | *line of play* | *left/right/central* | *player's market value* |

used by:
- Filtered Database to show a filtered version according to your settings in the filter
- Filtered Database to add your preferred players from

**My Squad Database**

|ID (unique)  | NAME            | CLUB            | POSITION       | SIDE                 | MARKET VALUE            |
| ----------- | --------------- | --------------- | -------------- | -------------------- | ----------------------- |
|*generated ID| *player's name* | *player's club* | *line of play* | *left/right/central* | *player's market value* |

used by: 
- Filtered Database to store your preferred players to
- My Squad to remove your preferred players from
- Show Team to select your team from
- Post Your Team to send your database

Activities structure:
---------------------
* [Home][]
	* [My Squad][]
		* [Show Team][]
	* [Database Filter][]
		* [Filtered Database][]
	* [Post Your Team][]

[Home]: https://github.com/Sjaakja/native_app/blob/master/doc/home.png
[My Squad]: https://github.com/Sjaakja/native_app/blob/master/doc/my_squad.png
[Show Team]: https://github.com/Sjaakja/native_app/blob/master/doc/input.png
[Database Filter]: https://github.com/Sjaakja/native_app/blob/master/doc/database_filter.png
[Filtered Database]: https://github.com/Sjaakja/native_app/blob/master/doc/filtered_database.png
[Post Your Team]: https://github.com/Sjaakja/native_app/blob/master/doc/post.png

Classes:
--------
* Home (extends activity)
* My Squad (extends activity)
* Show Team (extends activity)
* Database Filter (extends activity)
* Filtered Database (extends activity)
* Post Your Team (extends activity)
* SQLhandler (extend SQLiteOpenHelper)