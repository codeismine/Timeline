# Timeline
Code Challenge:

Created an App.net client that just lists the most recent posts from the public timeline.

Posts are be rendered in a ListView with the most recent at the top.
Each row contains the user’s avatar with rounded corners.
Poster’s name is in bold
Post text is of be variable height.
Pull to refresh is implemented to refresh the timeline
The list scrolls quickly, without dropping frames on a Nexus 5
The latest publicly released Android SDK is be used plus the support library is used to implement Material Design.
Third party libraries are allowed but are not used.
The timeline is fetched from: https://alpha-api.app.net/stream/0/posts/stream/global.


# Error checking

1) Internet connection is available

2) Data is available

3) If no text available than show message "No Description available"