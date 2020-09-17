# Project - 'PIGGYURL' - Solution approach

- Login module will be introduced where only authorized User is allowed to login.
- There will be 2 level:
   * Admin
   * User
- Role of Admin
   * An admin can add another Admin/ User.
   * An admin can create Group.
   * An admin can create Card which contains URL information.
   * An admin can approve the suggested changes for URL cards by the user.
   * An admin can view the list of Cards for a Group.
   * An admin can delete any user/ Group/ Cards.
- Role of User
   * User can view the list of Cards for a Group.
   * User can suggest change for any card.
- Feature for Cards.
   * For any given long URL, a card will be created which will have corresponding short URL with descriptions.
   * Shorten URL can be created on 4 level:
      * NONE
      * USER
      * TRIBE
      * FEATURE_TEAM
   * Shorten URL will change based on above selection.
   * Cards will have default expiration date (if not given as an input) which is configurable.
   
      