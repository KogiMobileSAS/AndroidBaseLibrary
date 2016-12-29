### Base Fragment
For each fragment on your project, extends to ``BaseFragment`` and you need to implement the base methods:
```java
    @Override
    protected void initVars() {}
    @Override
    protected void initViews() {}
    @Override
    protected void initListeners() {}
```
#### initVars()
In this method you can create the global variables of the class like adapters and get arguments of the fragment.
#### initViews()
Set the resources to the view like the setAdapter() to RecycleViews.
#### initListeners()
Set the listeners to the views.

#### Notes:
This Fragment manages the CompositeSubscription and EventBus of the fragment, doing the subscription or register and the unsubscribe or unregister from both components, you can access to the CompositeSubscription and add a subscription to Rx Observables using addSubscription() method.


###  Base Fragment MVP

This fragment can manage the attached and detached from the presenter of the fragment  ``BaseFragmentMVP<T extends BasePresenter>`` this is the representation of a generic class where you can use any class to extends off a BasePresenter and need to implement the BasePresenterListener although you can implement any listener attached on your presenter.

#### Notes:
Remember create the instance of the presenter on the ``initVars()`` method if you don't do that the fragment going to generate a RuntimeException.

### Base Fragment MVP List

This fragment is the implementation of a list with load more items by pagination and pulls to refresh elements. In this fragment we have trees
keys to be successful.

1 Implement ``BaseFragmentMVPList<P extends BasePresenter, M>`` where ``P`` is a presenter that need to extends from BasePresenter and ``M``going to be the object that the list is showing in the RecyclerView.

2 The XML view needs to contain a RecyclerView and a SwipeRefreshLayout and set this one to the super class with the methods ``setRecyclerView(@NonNull RecyclerView recyclerView)`` and ``setSwipeRefresh(@NonNull SwipeRefreshLayout swipeRefresh)`` seting on the ``initview()``.

3 Set te adapter with the method ``setAdapter()`` on the ``initVars()`` this adapter extends of the on ``BaseSimpleAdapter<M>``.

### BaseSimpleAdapter

is an abstract class with a generic class ''T'' and <H extends BaseSimpleAdapter.BaseViewHolder>. The first one is the model object that the adapter is going to use and the second one is the Holder to contain the view of the principal item. This Base Adapter has the idea to simplify the implementation of the adapter in the RecycleView and the different use case like the empty view, loading views, and loads more views.

#### Tools

* List of items List<T> items = new ArrayList(). is the items that the adapter is going to paint on the views.

* BaseViewHolder<T> is the base of all the holders in the adapter, this one extends from RecyclerView.ViewHolder and manage the ButterKnife.bind and has the abstract method bindView(T item). This method is the place where you going to fill and manage your views.

* EmptyViewHolder<T> is the Base of the generic view if you don`t need the update or bind your view you can use this holder. By example with the empty view, loading views, and loads more views.

* onItemClickListener<T> is the listener to manage in simple click this listener can be setter with the addClickListener(onItemClickListener<T> clickListener) is recommended set this method on the initVars(). This listener implements the method onItemViewsClick(T item, int position) where you can get the item and the position of this one inside the list of items. You can use the callItemListenerByPosition(int adapterPosition) to manage the click and the position of the item this is because if you have a header the position of the adapter is different to the position of the item in the list<T>.

* Type if view
´´´java
   protected static final int EMPTY_VIEW = 2000;
    protected static final int HEADER_VIEW = 3000;
    protected static final int LOADING_VIEW = 4000;
    protected static final int LOAD_MORE_VIEW = 5000;
´´´
This types can be caller by the method onCreateViewHolder(ViewGroup parent, int viewType) to identify the different views and set the correct view holder, the items don`t have a view type by default for that you can assume if the view type is no one of this ones is an Item view.

* LOADING_VIEW and LOAD_MORE_VIEW to use the loading view you only need indicate the enable of the view using  showLoadingState(boolean loadingState) sending true or false if need to show or not the loadinview.

* cleanItemsAndUpdate() and addItems() are methods to set data to the list of items in the adapter. Yo can use other auxiliary methods to manage the date of the list like:
´´´java
getItem(int position);
removeItem(@NonNull T item);
removeItemByPosition(int position);
addItem(@NonNull T item);
updateItemByPosition(int position, @NonNull T item);
addItemByPosition(@NonNull T item, int position);
getItemPosition(int adapterPosition);
´´´
note: T is the Object to manage on the view in the adapter.







