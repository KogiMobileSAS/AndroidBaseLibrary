##Events
All base events are registered in the BaseActivity and can be handle by this one without any additional implementation.
#### EventAlertDialog
With this event, you can launch an AlertDialog by EventBus and set to this one the title, message, positive button and negative button using the builder pattern.
#### EventSnackbarMessage
With this event, you can launch a basic SnackBar by EventBus and set to this one the message to show and the possible action that you needed.
#### EventProgressDialog
The EventProgressDialog let you launch a basic progress and set to this one the message to show.
#### EventToastMessage
The EventToastMessage let you launch a basic toast and set to this one the message to show. Note: The toast is deprecated in the department, try to use SnackBar instead.
#### EventGhost
This event is a requirement of the library EventBus to prevent a problem on the BaseFragment.java