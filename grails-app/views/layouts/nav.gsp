<div class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="../../">Docker Registry</a>
            <button data-target="#navbar-main" data-toggle="collapse" type="button" class="navbar-toggle">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>

        <div id="navbar-main" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="dropdown">
                    <a id="themes" href="#" data-toggle="dropdown" class="dropdown-toggle">Images</a>
                </li>
            </ul>

            <g:form class="navbar-form navbar-right" controller="search" action="search" method="GET">
                <input name="q" type="text" placeholder="Search images..." size="40" class="form-control col-lg-8">
            </g:form>

        </div>
    </div>
</div>