                   Miranda Instant Messenger Release Notes
                   ---------------------------------------

                             March 2007 (v0.6.8)

                          http://www.miranda-im.org


Contents
--------

1. New in this version
2. Installation instructions
3. General notes
4. Troubleshooting
5. Compatibility issues
6. Contact
7. Support
8. License and Copyright



1. New in this version
----------------------
* New in 0.6.8
Bugfixes:
 - AIM: Fixed issue with outgoing messages with double quotes
 - AIM: Fixed issue with converting links in incoming messages
 - IRC: Fixed crash with "perform" command in IRC options tab
 - Jabber: Fixed auth error via SSL ("SSL negotiation failed")
 - Jabber: Fixed OS version detection under Windows Vista
 - MSN: Fixed idle mode support
 - MSN: Fixed typing notifications
 - MSN: Improved group handling
 - MSN: Improved file transfers

* New in 0.6.7
Bugfixes
 - Jabber: Fixed crash on exit when using Jabber
 - MSN: Uploading avatars was broken

* New in 0.6.6
Bugfixes
 - Fixed dragging/dropping file with non-locale characters into message window
 - Logging large chunks of data to disk could cause crash
 - Fetching events from the database sometimes failed
 - Sounds in message windows didn't play properly

* New in 0.6.5
Bugfixes
 - Update notification module not loaded correctly

* New in 0.6.4
Enhancement
 - Update notification added to notify of new Miranda IM releases
 - Improved dbTool and now supports language packs (translations)
 - Some cosmetic changes to the profile selection dialog

Bugfixes
 - "Read Message" hotkey opened contact list if no events were present
 - Translation fix in Find/Add error message
 - Improved UPnP support and disabled UPnP cleanup when no incoming connections available
 - Show file transfers correctly in message window
 - Improved UPnP router support
 - UPnP cleanup will not run if there are no incoming connections

* New in 0.6.3
Bugfixes
 - Pin to Desktop was broken
 - AIM: Auto response messages weren't shown as auto responses

* New in 0.6.2
Enhancements
 - Improved unicode support in path utilities
 - UPnP routers that use chunked encoding are now supported
 - Added ability to enable/disable sounds globally

Bugfixes
 - Tooltips on button controls displayed incorrectly in ANSI version
 - Enabling/disabling first group of sounds in options didn't enable apply button
 - Path in sound options was cut off for long paths
 - Some dialog backgrounds were not using system colors
 - Global hotkeys didn't work correctly
 - AIM: Messages were not received properly
 - AIM: Fixed formatting of outgoing unicode messages (AIM 5.9 was affected)
 - ICQ: Fixed compatibility issues with ICQ 6
 - ICQ: Fixed Meebo client detection

* New in 0.6.1
Enhancements
 - Added option to enable/disable UPnP support
 - Improved control/shift/enter behavior in chat windows
 
Bugfixes:
 - History button in chats didn't open history
 - Fixed network bind plugin compatibility issues with older plugins
 
* New in 0.6
Enhancements
 - Implemented UPnP support
 - Core support for memory operations, strings, utf8 support, threading
 - Tabbed options support
 - ICQ: GDI resources optimization for extended status icons
 - Jabber: Support for NTLM & MD5-DIGEST auth modes
 - MSN: Support for WLM8 direct transfers
 - MSN: File transfers added for the HTTP gateway mode
 - MSN: Improved keep-alive packets sending
 - Jabber: Ability to remove your account from the server
 - Jabber: Ability to resolve nick names for transports

Bugfixes:
 - AIM: Fix for file transfers
 - ICQ: Some fixes for avatars
 - IRC: Fix for nicknames which contain spaces
 - Jabber: Added language id to the login request ('en')
 - Jabber: Jabber crashes on very large images in vCards
 - Jabber: Fix for issues with backslashes in the transports names
 - Jabber: Fix for sending files to Pandion IM
 - MSN: Improved error 217 (contact already exists) processing.

* New in 0.5.1
Enhancements
 - ICQ: Added new session password API - allowing plugins like Secure Login to work better
 - Jabber: Improved the compatibility with the GTalk clients:
   - Typing notification (JEP-0085);
   - Avatars retrieving and publication (JEP-0153).
 - Jabber: SASL authentication implemented, to allow the TLS usage. For old
   servers which don't support SASL, the special option was added to disable
   SASL, it guarantees the full compatilibily with almost all servers.
 - Yahoo: Show a Popup for System Messages.
 - Yahoo: Improvements to the request handling when behind HTTP/HTTPS proxies
 - Yahoo: Added System Message handling (via Popup).

Bugfixes:
 - Cannot create new profile through startup dialog (under NT4/95)
 - Crash reading language pack headers
 - Options dialog didn't activate if currently open when using the main menu item
 - Profile selector didn't show last modified time correctly
 - db3x driver text was cut off in profile selector dialog
 - AutoExec files were deleted after use instead of renaming
 - Allow use of '#' as the beginning character in a autoexec file
 - Typo in about dialog
 - Importing from autoexec_*.ini now supports CP1251 and UTF-8
 - AIM: Fixed login problems from previous login order change
 - AIM: Fixed utf8 sending for aim clients
 - AIM: Keepalive timer fixes
 - AIM: Changed the login order to force aol to send us a list on login
 - AIM: Changed group handling to not move manually added users to their actual group
 - AIM: Changed group handling to not lowercase all server-side group names
 - AIM: Added meebo client detection
 - AIM: Added adium client detection(beta adium only)
 - AIM: Modified the gaim/adium detection to show as only gaim detection
 - AIM: Fixed file transfer directory opening bug.
 - AIM: Apply button in options got enabled by default
 - AIM: Require "You were added" on new contact additions
 - AIM: Fixed a memory leak on deny file transfer
 - AIM: Removed extra mail service request at login
 - AIM: Fixed problems with initial signon as away when "Do not reply to request for this message" is set
 - ICQ: Fixed possible hang on exit
 - ICQ: Fixed avatars were not working when server port set to 0
 - ICQ: Made message parser more strict (fixes bug allowing stealth invisible client detection)
 - ICQ: Fixed Popup's unicode support cannot be used in ANSI mode even if available (caused empty Popups)
 - ICQ: Fixes possible buffer overflow with contact status change
 - ICQ: Fixed migration was not working at all (should not disconnect so often on server's upgrading)
 - ICQ: Fixed offline greeting cards were not processed properly
 - ICQ: Fixed small protocol glitch with reverse peer-to-peer connection
 - ICQ: Fixed HTTP/HTTPS proxy support (removed useless gateway option), long messages are now sent properly
 - Jabber: Fixed invalid invitations in groupchats
 - Jabber: JEP-0077 compliance fixed
 - MSN: Fixed sending files to old Windows Messengers (4.x-5.x)
 - MSN: Fixed direct avatars transfers for Windows Live Messenger
 - SRMM: Improved RTL support
 - Yahoo: Fixed avatar upload bug
 - Yahoo: Show a system message when a file is successfully uploaded to Yahoo servers.
 - Yahoo: various small fixes.

* New in 0.5
Enhancements:
 - Unicode support was introduced
 - Database can store the Unicode strings (in utf8 format), and save and return these strings as ANSI, UCS2 and UTF8 strings
 - Most core protocols can send, receive & process the information in Unicode
 - The contact lists can display nicks, names and statuses in Unicode (under NT)
 - Message editors can display any user information in Unicode, display and enter the Unicode messages;
 - User details can process names, addresses, etc in Unicode
 - Language packs can provide the valid translation for the Miranda's interface, even when the default codepage doesn't match the langpack codepage
 - Two Miranda distributions are available since 0.5: the ANSI (for Win95/98/ME) and the Unicode one (for NT4/Win2k/XP/Win2003)
 - The standard was designed for all contact list plugins, to simplify the support for sorting, storing, grouping contacts
 - png2dib plugin was inserted into the distribution to provide the core-level support for PNG files
 - Support for sending files with the Unicode names (not mappable into the current codepage)
 - AIM: AIM protocol now using Oscar
 - ICQ: Messaging support enhanced (P2P messaging supported)
 - ICQ: Direct Connection support rewritten (with P2P messaging and reverse file-transfers)
 - ICQ: Temporary Visible List support
 - ICQ: New Features page in options to configure extra features
 - ICQ: Custom statuses just like icq5 - needs clist_mw derivative (thx Big Muscle)
 - ICQ: New custom status icons (thx Angeli-ka)
 - ICQ: Enhanced Unicode & Ansi charsets support (each contact can have its own codepage)
 - ICQ: AIM cross-compatability (add/remove, search by mail, status, messaging, away messages)
 - ICQ: Secure MD5 login authentification
 - ICQ: Revoke authorization (Advanced Feature - see further) - removes you from contact's server-list
 - SRMM: RTL support for messages in Hebrew and some other languages.
 - Jabber: Avatars support (JEP-0008)
 - Jabber: Massive groupchat improvements, including private messages & invitations
 - MSN: Client version detection
 - MSN: Support for nudges
 - MSN: Support for some MSN Messenger 7.5 and Live Messenger 8.0 specifics
 - Yahoo: Ability to set your own avatar
 - Yahoo: Added new icons
 - Yahoo: Added address book and calendar links in the main menu
 - Yahoo: Added alerts support and link to the main menu
 - Yahoo: Added nick to Yahoo login information
 - Yahoo: Yahoo now uses new png2dib.dll services for avatar manipulation
 - Yahoo: Added Yahoo Japan support
 - Yahoo: Added buzz/nudge support
 - Yahoo: Offline IMs from blocked people get dropped
 - Yahoo: Added basic audible support
 - Yahoo: Yahoo File Transfer improvements

Bugfixes:
 - Clist classic: Right click on a tray icon results to the resource leak
 - ICQ: Our own contact in list now correctly handles events
 - ICQ: Proxy Gateway mode is working again
 - ICQ: File receiving from Trillian clients work again
 - ICQ: Message errors are now really displayed
 - ICQ: Fixed memory leak in avatar code
 - ICQ: File transfers with GnomeICU works now
 - ICQ: Fixed changing password in ICQ Details will not save it to DB if not there
 - ICQ: Fixed PING channel handling (caused connection drop)
 - ICQ: Fixed bug in changeinfo with translated listbox values
 - ICQ: Fixed error handling in avatar code for uploading avatar
 - ICQ: Fixed bug in Manage-Server list dialog, caused hang
 - ICQ: Fixed occasional losing of messages (message parsing was incomplete)
 - ICQ: Server-list management now stores all info - solves problems
 - ICQ: Fixed unknown errors during login were not reported sometimes
 - Jabber: resource names not UTF8 decoded
 - Jabber: people are trying to connect to jabber.org with empty usernames
 - MSN: Fix for the error 540 upon login, because of the invalid password hash calculation
 - MSN: Fixed the problem of locking the message sending queue by timeouts
 - MSN: Fixed disconnection issues for the HTTP gateway mode
 - MSN: Fixed some issues with the wrong Hotmail Inbox and Profile Editing URLs
 - MSN: Fix for error 911 inside switchboard threads
 - Yahoo: Fixed deletion of a buddy from your contact list
 - Yahoo: Made fixes to make more strings translatable
 - Yahoo: Fixed a bug that could crash srmm
 - Yahoo: Properly close the connections

* New in 0.4.0.3
Bugfixes:
 - ICQ: Fixed problem with sending and receiving messages.

* New in 0.4.0.2
Bugfixes:
 - AIM protocol uses TOC2 (TOC1 blocked by AOL)

* New in 0.4.0.1
Bugfixes:
 - Plugin options are not replaceable by a plugin
 - Sending files from the root directory added an extra '\'
 - Transparency focus issues in the contact list
 - Log didn't scroll to the bottom when showing avatar in message window
 - Trailing spaces removed from search by user id and email
 - Button control didn't always draw icons correctly
 - Sending files from the root folder used the incorrect path name
 - Contact list focus on mouse-over didn't work
 - Issues writing blobs to the database
 - AIM: aim: links didn't register correctly
 - ICQ: Renaming of server groups was not working properly - caused errors
 - ICQ: Avatar could not be deleted (the delete button did not work)
 - ICQ: File Transfers were not working properly in some cases (wrong cookie handling)
 - ICQ: Avatar formats were not recognized correctly
 - ICQ: Method of determining target dir in file receive was not solid enough
 - ICQ: Manage server-list dialog leaked memory
 - ICQ: Synchronize visibility items was not working properly
 - ICQ: Our avatar image was not linked to our ContactPhoto
 - ICQ: Fixed occasional crash on login (missing TLV validity checks)
 - ICQ: Fixed slow update of nicks when users imported from server-list
 - ICQ: Fixed auto info update mechanism, do not progress too fast, do not drop processing
 - ICQ: Fixed empty groups are always hidden in Manage Server List, cannot be used either
 - ICQ: Fixed occasional crash on avatar retrieval - limit size of image to the size of packet
 - ICQ: If our rate is high, ignore user requests for status msgs & user details (prevents disconnection)
 - ICQ: Added temporary solution for roughly translated ICQ 2003b russian
 - ICQ: Manage server-list dialog could display other contacts and could crash
 - ICQ: Basic search could search for bad uin - garbage can be in the string
 - Jabber: Miranda process do not die when shutdown
 - Jabber: XML specials in add contact
 - Jabber: Status-visibility for a contact not working correctly
 - Jabber: Cannot transfer File(s) between miranda/jabber to/from PSI or Exodus (or more)
 - MSN: No zero-termination after _snprintf (potential security issue)
 - MSN: Can't send messages to the MSN WebMessenger
 - MSN: Can't delete contact's directory for received files

Enhancements:
 - Prevent plugins from overriding the plugin options dialog
 - Prevent useless database writes
 - AIM: addbuddy link support
 - ICQ: If Update details from server is on, user group are also updated
 - ICQ: Changed System Uptime to Member since in my details
 - ICQ: Auth system recognizes & sends UTF-8 messages
 - ICQ: Miranda version signature improved (preparing for old signature removal in the future)
 - ICQ: Added better error detection for offline msgs receival process
 - ICQ: Made avatar handling more resilient to server errors
 - Jabber: Support for the chat plugin
 - Yahoo: Adding gaming status

* New in 0.4
Bugfixes:
 - Crashes after warning about unsuccessful search
 - ESC doesn't work in User Detail
 - Phrases with leading and trailing spaces not translateable
 - Incorrect Local time in Userinfo
 - CheckDlgButton didn't draw correctly if used after creation in button control
 - Offline users weren't always shown in Typing Notify Options
 - Ctrl+w closes message window
 - Moving a group outside the CLC causes a crash
 - Crash translating a menu
 - Scrollbar still appeared sometimes if XP skinning was enabled
 - Contact list control read-disk-on-paint bugs
 - Many database bug fixes (crashes, corruption)
 - Crash opening Find/Add dialog
 - No & sign in the Open File button on the File Transfer window
 - Visual defect in "Options\Contact List\Icons" when Expert options unchecked
 - AIM: Removing user from deny list didn't always update mode in Miranda
 - AIM: Fixed file transfers with Mac users
 - ICQ: Error alert blocked I/O
 - ICQ: Newly added contact with privacy setting is not hidden anymore
 - ICQ: Offline messages are no longer received older than existing ones
 - ICQ: Fixed authorisation reply
 - ICQ: Fixed contact transfer ack
 - ICQ: Now parses URL send ack correctly, no more timeout
 - ICQ: Now sending ack on contacts receive
 - ICQ: Fixed crash on receiving long message through P2P
 - MSN: Connection/login fixes
 - IRC: Many bug fixes
 - Yahoo: Much cleaner/better/stable code

Enhancements:
 - Database and contact list control moved to plugins
 - Relative paths for icons, sounds, and contact list background
 - Scroll through past messages with ctrl+up/dn in message window
 - New idle module to handle autoaway and protocol idleness
 - Pin to desktop option in Window options
 - Dim idle contacts option
 - Use windows colours option for contact list
 - New status bar options (hide bevel, hide gripper)
 - Database I/O improvements
 - Option to hide vertical scroll bar
 - Opening a URL blocked I/O
 - AIM: Show mobile users as 'on the phone'
 - MSN: Sending/receiving avatar support
 - MSN: Peer to peer support
 - ICQ: Added avatar tab to user-details dialog to show avatar
 - ICQ: My user-details now show more information & added idle since to ICQ tab
 - ICQ: Added full unicode message support (requires SRMM Unicode)
 - ICQ: Added support for sending and receiving Idle time
 - ICQ: Added reliable client identification
 - ICQ: Added support for avatar images (downloading & uploading).
 - ICQ: Added Grant authorisation option (send & recognize)
 - IRC: Support for secured connections using OpenSSL
 - IRC: Support for CTCP and DCC filetransfers and chats
 - IRC: New graphical user interface using Chat plugin
 - Jabber: Unicode support
 - Yahoo: Better Unicode support
 - Yahoo: New Buddy Search
 - Yahoo: File Transfer support
 - Yahoo: Stealth support (Yahoo v6 visibility list)
 - Yahoo: Idle support
 - Yahoo: Avatar support (retrieval only)


* New in 0.3.3

Bugfixes:
 - Fixed crash when closing history window with large history
 - IE was always used as the default browser if IE was open
 - Added option to set outgoing port range
 - Several memleak fixes
 - Improved proxy handling
 - Rare crash on opening options
 - AutoNA was not set correctly when Away was set
 - Visibilty list did not list all users
 - Icons in URL and message contact menus didn't update when icons changed
 - Reduce CPU usage in file trnasfer dialogs (less screen updates)
 - Many other bug fixes
 - AIM: Unable to save "Only reply to users in your contact list" option
 - ICQ: Failed to send or receive files from ICQ 2003b.
 - ICQ: Fixed a number of smaller memory leaks.
 - ICQ: Contact e-mail info was not displayed correctly.
 - ICQ: Failed to retrieve user details during certain circumstances.
 - ICQ: URL messages could disappear when sent through a Direct Connection.
 - ICQ: Nick name was not deleted from server list when local nick name was deleted.
 - ICQ: Server side contacts could reappear after being deleted if they were on the visible/invisible lists.
 - ICQ: Changing status while connecting had no effect.
 - ICQ: A bunch of other fixes the CVS changelog if you want the big list.
 - Jabber: Should offline all chatrooms when go invisible (because they are actually offline)
 - Jabber: Miranda won't exit if more than one groupchat windows are left open
 - Jabber: Wrong filename encoding in file sending (signed/unsigned problem)
 - Jabber: Crash on connect/disconnect caused by stale contacts associated with an unknown protocol
 - Jabber: Deleting groupchat temporary contact unintentionally closes the chatroom
 - Jabber: Presence is broadcast to chatroom that has denied our access
 - Jabber: Graceful exit while Miranda shutdown in the middle of file transfer
 - MSN: A port remained opened after the file trasnfer cancel
 - MSN: if a contact list is very long, all contacts could be shown in the Offline mode
 - MSN: When you set your own nickname with spaces, it appears in the options dialog URL-encoded (with %20 instead of a space char).
 - MSN: Opened switchboard sessions weren't closed when you go offline without closing Miranda.
 - MSN: File senging does not require to talk anymore
 - MSN: Contacts with leading digits in the e-mail were not processed properly
 - MSN: Many other bug fixes

Enhancements:
 - New user is typing API (supported by ICQ/MSN/Jabber/Yahoo)
 - Messaging module moved to a plugin with many UI changes (SRMM)
 - AutoAway can now set protocols away if the workstation becomes locked
 - Improved button control (bitmap support, flat button support)
 - Added options to disable systray icon flashing and adjusting blink time
 - Default icon packs are automatically loaded from disk if they exist
 - Added report bug menu item
 - Many other enhancements
 - AIM: Added user search capability
 - AIM: Send messages to groupchat users by double clicking name
 - AIM: Added file receive support
 - ICQ: Added support for sending and receiving Typing Notifications.
 - ICQ: Now accepts messages formatted in unicode (note: this wont solve the problem with displaying messages with multiple charsets).
 - IRC: Contacts on the contactlist
 - IRC: Hostmasks
 - IRC: Ignore system
 - IRC: Channelmanager
 - Jabber: Manual registration to Jabber services
 - Jabber: Can automatically remove contacts not in roster
 - Jabber: Invite and accept invitation to a groupchat
 - Jabber: Support composing message event (message typing notification)
 - Jabber: Send full JID (with resource name) with <message/> when appropriate
 - MSN: 'User is typing' support added
 - MSN: "Invite to chat" contact menu item is added.
 - MSN: 'block/unlock' feature is integrated with Options->Status->Visible
 - MSN: New gateway support


* New in 0.3.2

Bugfixes:
 - Buffer overrun in url dialogs
 - Options window was shown off screen if previously closed while minimized
 - Some button controls were not translatable
 - Fixed issues with duplicate protocols listed in the icons options
 - AIM: Login flood when creating new users
 - AIM: Automatically turn off server-side list support for list that are to large (AIM)
 - AIM: Crashed when truncating large packets
 - AIM: Crash parsing empty server-side list
 - AIM: Warn User menu in groupchat was always disabled
 - Jabber: Better disco/browse fallback scheme for groupchat browsing
 - Jabber: Undecoded room jid in groupchat log window title
 - Jabber: Connection thread can lingering around while in reconnect loop and Miranda exits
 - Jabber: Changing global statuses to an unsupported one shows correct icon now (a bug introduced again in 0.1.1.2)
 - Jabber: Initial status in chatroom not correct
 - Jabber: Crash when displaying x:data list-single that has no default selection
 - Jabber: Multi-line x:data field truncated to first line
 - Jabber: Adding contacts when already exist in clist with "Hidden" does not remove "Hidden"
 - Jabber: Won't send ASCII control chars that are invalid XML chars (replaced with spaces)
 - Jabber: Properly offline when set offline while connecting
 - Jabber: Initial data sometimes not shown on agent dialog
 - Jabber: Connection thread still not exit properly when Miranda exits
 - Jabber: Another change in setstatus/setawaymsg handling
 - Jabber: Password dialog change to modeless
 - Jabber: Discard whitespaces not enclosed by any  XML tags so that they won't waste buffer space
 - MSN: Cookies usage had been turned off, it means that you can logon automatically into MSN Messenger and simultaneously logon using Miranda under different account (thanks to Pixador).
 - MSN: WinInet.dll now gets unloaded from memory after login to use less memory.
 - MSN: Attemp to find/add a contact that already exists in your contact list resulted to strange error message.
 - MSN: Authorization problems when many MSN accounts are used in the same Miranda installation.
 - MSN: If a password contains non-alphabetical characters (like punctuation, ampersand, plus, etc) the MSNP8 login could return error 401 Unauthorized. MSNP7 login works Ok.
 - MSN: If a contact changes its status to Invisible, it's not possible to send messages to him/her anymore.
 - MSN: The popup displaying procedure was changed to avoid so called 'frozen popups'effect.
 - MSN: New MSN Menu item was added to edit user's MSN profile in a browser
 - MSN: Authorization problems when many MSN accounts are used in the same Miranda installation.
 - MSN: If a password contains non-alphabetical characters (like punctuation, ampersand, plus, etc) the MSNP8 login could return error 401 Unauthorized. MSNP7 login works Ok.
 - MSN: If a contact changes its status to Invisible, it's not possible to send messages to him/her anymore.
 - MSN: the popup displaying procedure was changed to avoid so called 'frozen popups'effect.
 - ICQ: Prevent your status messages being read when you are invisible.
 - ICQ: Small memory leak when sending an SMS.
 - ICQ: Fixed a dumb bug that caused random disconnections from the ICQ server.
 - ICQ: Cleaned up the code for searches and user info updates, should work better now.
 - ICQ: AIM users in your server contact list would get added locally with uin 0.

Enhancements
 - Docking to the left or right side of the screen only happens when CONTROL is held down.
 - Possible to prevent writing of single key to registry on startup.
 - Possible to specify ports for outgoing connections.
 - Loading large histories now shows window immediately.
 - AIM: Added option to edit display name.
 - Jabber: Groupchat admin functions.
 - MSN: The SSL autorization procedure was changed: now it uses Internet Explorer's proxy settings to log in. It also resolves problems with proxies that require authorization (previously you could see the error 407 in the network log when trying to logon). In this case MSN plugin tries automatically to apply the login and the password from the Options -> Network -> MSN.
 - MSN: Error diagnostic has been slightly enhanced.
 - MSN: New MSN Menu item was added to edit user's MSN profile in a browser.
 - MSN: MSNP7 support is deleted and this option is locked
 - MSN: Netmeeting support added
 - MSN: Option was added to launch a specific program when the new Hotmail arrives
 - ICQ: Added "missed message" notification.
 - ICQ: Added better error messages for message send failures.
 - ICQ: Messages now default to the most reliable delivery method available for a given contact.


* New in 0.3.1

Major changes:
 - IRC protocol plugin is now included in the installer

Bugfixes:
 - Fixed tooltips cutting off text in systray on newer shells
 - Removed some memory leaks
 - Many bug fixes relating to the away system
 - CPU went to 100% if resized to small
 - Possible XP theme corruption in toolbar image buttons
 - Find/Add dialog showed protocols that didn't support the find/add service
 - Rename contact menu item is only shown when menu is shown from the contact list
 - Netlib could cause 100% CPU usage during fast file transfers
 - Menu items could sometimes be placed in the wrong order

Enhancements:
 - Redesigned message dialog
 - Added a heap compactor to flush out memory every 5 minutes
 - All new default icons
 - You can now set a sound for outgoing message
 - Autoaway no longer pops up change status message dialog
 - Added a Global Status Icons setting under icons. This lets you
   set the default systray icon and global status menu icons.


* New in 0.3

 Major changes:
 - ICQ module has been moved to a separate plugin. You can now
   run Miranda IM without ICQ
 - Bundled support for ICQ, AIM, Jabber and MSN

 Bugfixes:
 - Reply quoted sometimes closed window
 - Much safer multithreading. This will help make Miranda IM more stable.
 - Several "crash on exit" bugs fixed
 - If only specific protocols are invisible don't show contact's as always
   visible if their protocol is not invisible
 - Strange behaviour in the Auto away feature
 - New add/authorization dialog
 - Always/Never visible settings is now mutually exclusive
 - If already away or in NA, don't try to resend away/NA, this fixes the
   problem with the NA dialog reshown every five seconds, also fixes flooding
   of NA status changes to other contacts
 - File Transfer Complete sound would play even if that never happened, added
   a few extra sounds for "failed" and "denied".
 - Smarter database: Miranda now checks your profile carefully before opening to ensure that it's in proper    working order
 - Since the database is so smart, it also checks to see if your disk drive has actually
   written out new information, if it hasn't it shuts down, protecting the profile from major
   corruption (total data loss of a profile infact)
 - At least 2 gazillion internal fixes too complicated to explain (we dont
   even know what we did ;))

 Enhancements:
 - UI improvements to the plugins options page
 - Search dialog now remembers last protocol used
 - Added possibility to delete user with Del key
 - Improved integration with XP themes
 - Added menu in URL receive dialog (open in new/current window/copy link)
 - Multi line tray tooltips
 - Netlib now creates log path if it doesn't exist
 - Several optimizations to make Miranda IM run even faster and more stable
 - Made it possible to close read status message dialog with escape
 - Improved some of the error messages to make them easier to understand

* New in 0.2.1
 - Sending messages to ICQ users who are in 'Invisible' mode does not work
   if 'Send messages slower, but with full acknowledgement' option is enabled.
 - Sending message to users with old ICQ versions sometimes does not work.


* New in 0.2
 - Support for SOCKS4, SOCKS5, HTTP and HTTPS proxies.
 - Improved stability.
 - Some general user interface changes.
 - New default icons.
 - Faster filetransfers.
 - Import contacts and history from other Miranda profiles.
 - Import contacts and history from Mirabilis ICQ 99a-2003a.
 - Support for more plugins (>120 plugins available for download!).
 - more than 160 other bug fixes and improvements!
 - Ability to store and download contacts from the ICQ servers.
 - Increased compatibility while communicating with other ICQ clients.
 - Many bug fixes related to file transfers.
 - Allow/don't allow direct connections.
 - Enable/disable 'Web aware'.
 - Don't allow unknown contacts to read your status message.
 - Optionally send requests for authorizations.
 - Optionally send 'You have been added' notifications.


* New in 0.1.2.1
 - Improved contact list with support for background images,
   nested groups and lots more
 - Nifty new context-sensitive help plugin
 - Send messages to many people at once
 - Drag-and-drop file sending
 - Improved search and icq whitepages search
 - HTTP proxy support for ICQ
 - Options dialog revamp. Shows Expert mode option
 - More bug fixes than you can shake giant redwood at


* New in 0.1.2
 - Connects to the new ICQ servers for much more reliable communication
 - Improved support for connecting to other instant messaging networks
 - New user details dialog box
 - File transfer that actually works
 - Supports an SMS messaging plugin
 - Supports a plugin to change your own details from within Miranda
 - URL Clicking Support in Split Message Dialog Plugin
 - More bug fixes than you can shake giant redwood at


* New in 0.1.1
 - Setting Status Messages. E.G. Away/Occupied/DND....
 - Visible/Invisible List. E.G. Be invisible to a specific user
 - Import Plugin can now import History in addition to Contacts
 - Language Pack Support
 - File Transfer Dialog Improved



2. Installation Instructions
----------------------------

* Upgrading

To upgrade an existing Miranda IM installation, run the installer
and choose to install to the same folder. Your message history,
settings and contact list will be saved.

* First Installation

Run the installer and follow the instructions.



3. General Notes
----------------

Unlock the power of your new Miranda IM with plugins! There are now more
than 200 plugins available as free downloads:
http://www.miranda-im.org/download/index.php



4. Troubleshooting
------------------

If you have problems with Miranda:

a) Disable all plugins. If the problem is gone now, it was probably a
   bug in one of the plugins and not in Miranda IM itself.
b) Update to the latest alpha version.
   We regularly fix bugs in Miranda IM. If you are having a problem it
   might help to upgrade to the latest alpha version, maybe we already
   fixed it. You will find the "Nightly alpha builds" in the "Development"
   section of our website.
c) Check the list of known bugs.
   The official buglist is available at:
   http://bugs.miranda-im.org/
   Look here before you submit a new report, dont submit a new report if
   it has already been reported.
d) Submit a bugreport.



5. Compatibility issues
-----------------------

Miranda IM supports all released versions of Windows from
Windows 95 to Windows XP. Beta versions are not supported.

a) Windows 95

 - If you have trouble running Miranda IM on a Windows 95, make
   sure that you have Winsock and DUN upgrades installed.
 - Menu icons are not supported in Windows 95.
 - Window transparency is only supported on Windows 2000 and
   Windows XP.

b) IRC plugin and MSVCP60.DLL
 - The IRC plugin requires MSVCP60.DLL. If you dont have this
   file on your system, download it from:
   http://www.dll-files.com/dllindex/dll-files.shtml?msvcp60

c) Plugins
 - Third party plugins may have different requirements than
   Miranda IM itself. The Miranda IM developers cannot guarantee
   that all plugins will work on your system.



6. Contact
----------

a) Webpage

The official webpage for the Miranda IM project is www.miranda-im.org.

b) Authors

Martin Öberg		 - Project Manager / Developer
			   strickz at miranda-im.org
Robert Rainwater	 - Developer
			   rainwater at miranda-im.org
Sam			 - Developer
			   egodust at miranda-im.org
Lyon Lymliz		 - Webmaster
			   lynlimz at miranda-im.org
Santithorn Bunchua	 - Jabber developer
George Hazan		 - MSN developer
Jörgen Persson		 - IRC developer


Miranda IM is made possible with the help of many more people.
A longer list of contributors can be found in the About box and
on the webpage.

Note: The developers do not provide user support by e-mail. See
section 7 for more information.

The source code is available at http://www.miranda-im.org/release/download.php



7. Support
----------

Miranda IM is provided free of charge but without any warranty or support.

If you have difficulties using Miranda IM, you can find a lot of resources in
the Help section of the website: http://www.miranda-im.org/help/

You can also ask for help in the official forums and someone will try to help.
But remember; everyone involved in the Miranda IM project are volunteers, as
in "does not get paid". So please be friendly and your chances of getting help
will increase.



8. License and Copyright
------------------------

Miranda IM is released under the terms of the GNU General Public License. See
"License.txt" for more details.

Miranda IM is copyright 2000-2003 by Richard Hughes, Roland Rabien,
Tristan Van de Vreede, Martin Öberg, Robert Rainwater, Sam Kothari, and Lyon Lim.

The plugins included with this release is copyrighted by their authors. See the
documentation for each plugin for more information.
