# GridViewPager

Scroll pages both horizontally and vertically in a solid grid structure.
Overscroll effects in every direction.

## Getting Started

To include the library using Gradle, add the following to your root build.gradle at the end of repositories:

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

And then the dependency:

```
dependencies {
	compile 'com.github.LucaBL:GridViewPager:v1.0'
}
```

## How to use

Create object in java or get it from xml.
In java, provide data inside the constructor.
From xml, provide data through initialize().

Java case:

```
GridViewPager pager = new GridViewPager(context, gridSizeX, gridSizeY, initialCenter,
        new GridViewPager.PageRequestCallback() {
            @Override
            public View getPage(int gridPositionX, int gridPositionY) {
                // Return the View for this specific grid position
            }
        },
        // This PageSelectionCallback can be null
        new GridViewPager.PageSelectionCallback() {
            @Override
            public void pageSelected(int gridPositionX, int gridPositionY) {
                // This gets called when a page is selected, if you need it
            }
        });
// add it to the parent view like parent.addView(pager);
```

Xml case:

```
GridViewPager pager = (GridViewPager) this.findViewById(R.id.pager);
pager.initialize(context, gridSizeX, gridSizeY, initialCenter,
        new GridViewPager.PageRequestCallback() {
            @Override
            public View getPage(int gridPositionX, int gridPositionY) {
                // Return the View for this specific grid position
            }
        },
        // This PageSelectionCallback can be null
        new GridViewPager.PageSelectionCallback() {
            @Override
            public void pageSelected(int gridPositionX, int gridPositionY) {
                // This gets called when a page is selected, if you need it
            }
        });
```

You can either use the parameters "int initialX, int initialY" to specify the exact initial grid position (with a different initialize() or constructor method), or "boolean initialCenter" like in the example to just tell if it's gonna be the center or the origin (0,0).
Here is an example:

```
GridViewPager pager = new GridViewPager(context, gridSizeX, gridSizeY, initialX, initialY, new GridViewPager.PageRequestCallback() {...}, null);
```

Here is a bigger example inside a generic Activity

```
private static final int gridSizeX = 3;
private static final int gridSizeY = 5;
private final boolean initialCenter = true;
private static GridViewPager pager;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    pager = (GridViewPager) this.findViewById(R.id.pager);
    // You can specify the exact initial grid position (with a different initialize() method), or just if it's gonna be the center or the origin (0,0)
    pager.initialize(this, gridSizeX, gridSizeY, initialCenter,
        new GridViewPager.PageRequestCallback() {
            @Override
            public View getPage(int gridPositionX, int gridPositionY) {
                // Return the View for this specific grid position
            }
        },
        // This PageSelectionCallback can be null
        new GridViewPager.PageSelectionCallback() {
            @Override
            public void pageSelected(int gridPositionX, int gridPositionY) {
                // This gets called when a page is selected, if you need it
            }
        }
    );
}
```
