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

Here is an example inside a generic Activity

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
