<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:if test="${not empty keyword}">
    <div class="container my-4">
        <h4>Kết quả tìm kiếm cho: "${keyword}"</h4>
    </div>
</c:if>

  <!--products-->
  <c:if test="${id_category==null}">
         <c:forEach var="c" items="${listCategory}">
        <section>
            <div class="container my-5">
                <header class="mb-4">
                    <h3>${c.getName()}</h3>
                </header>
                <div class="row">
                   <c:forEach var="p" items="${listProduct}">
                       <c:if test="${c.getId() == p.getId_category() && p.isStatus()==true}">
		    <div class="col-lg-3 col-md-6 d-flex">
                        <div class="card w-100 my-2 shadow-2-strong">
                            <img src="assets/images/${p.getImage()}" class="card-img-top" style="aspect-ratio: 1/1"/>
                            <div class="card-body d-flex flex-column">
                                <h5 class="card-type">${p.getName()}</h5>
                                <p class="card-text">${p.getPrice()} VND </p>
                                <div class="card-footer d-flex align-items-end pt-3 px-0 pb-0 mt-auto">
                                    <a href="
                                       <c:if test="${user==null}">
                                           login
                                       </c:if>
                                           <c:if test="${user!=null}">
                                           home?id_product=${p.id}
                                       </c:if>
                                       " class="btn btn-primary shadow-0 me-1">Add to cart</a>
                                </div>
                            </div>
                        </div>
                    </div>
                       </c:if></c:forEach>
                </div>
            </div>
        </section>
         </c:forEach>
</c:if>
        <!--products-->
  <c:if test="${id_category!=null}">
         <c:forEach var="c" items="${listCategory}">
             <c:if test="${c.id==id_category}">
        <section>
            <div class="container my-5">
                <header class="mb-4">
                    <h3>${c.getName()}</h3>
                </header>
                <div class="row">
                   <c:forEach var="p" items="${listProduct}">
                       <c:if test="${c.getId() == p.getId_category()}">
		    <div class="col-lg-3 col-md-6 d-flex">
                        <div class="card w-100 my-2 shadow-2-strong">
                            <img src="assets/images/${p.getImage()}" class="card-img-top" style="aspect-ratio: 1/1"/>
                            <div class="card-body d-flex flex-column">
                                <h5 class="card-type">${p.getName()}</h5>
                                <p class="card-text">${p.getPrice()} VND </p>
                                <div class="card-footer d-flex align-items-end pt-3 px-0 pb-0 mt-auto">
                                    <a href="
                                       <c:if test="${user==null}">
                                           login
                                       </c:if>
                                           <c:if test="${user!=null}">
                                           home?id_product=${p.id}
                                       </c:if>
                                       " class="btn btn-primary shadow-0 me-1">Add to cart</a>
                                </div>
                            </div>
                        </div>
                    </div>
                       </c:if></c:forEach>
                </div>
            </div>
        </section></c:if>
         </c:forEach>
</c:if>
