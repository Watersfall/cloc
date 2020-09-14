<%--@elvariable id="alignment" type="com.watersfall.clocgame.model.alignment.Alignment"--%>
<%@ page import="com.watersfall.clocgame.model.alignment.Alignment" %>
<%@ page import="com.watersfall.clocgame.model.map.year1900.Country1900" %>
<%@ include file="includes/top.jsp" %>
	<div class="title">${alignment.alignment}</div>
	<div class="tiling">
		<div class="column">
			<div class="tile">
				<svg id="world-map" version="1.1" viewBox="30.767 241.59 784.08 458.63" xmlns="http://www.w3.org/2000/svg">
					<g id="world">
						<%--@elvariable id="country" type="com.watersfall.clocgame.model.map.Country"--%>
						<c:forEach var="country" items="${Country1900.values()}">
							<c:if test="${country.alignment != alignment.alignment && country.alignment != Alignments.NEUTRAL}">
								<path style="fill: red" id="${country.attributes.get('id')}" transform="${country.attributes.get('transform')}" d="${country.attributes.get('d')}"></path>
							</c:if>
							<c:if test="${country.alignment == Alignments.NEUTRAL}">
								<path id="${country.attributes.get('id')}" transform="${country.attributes.get('transform')}" d="${country.attributes.get('d')}"></path>
							</c:if>
							<c:if test="${country.alignment == alignment.alignment}">
								<path style="fill: blue" id="${country.attributes.get('id')}" transform="${country.attributes.get('transform')}" d="${country.attributes.get('d')}"></path>
							</c:if>
						</c:forEach>
					</g>
				</svg>
				<c:if test="${alignment.alignment == Alignments.NEUTRAL}">
					<div class="pagination">
						<a href="${Alignments.CENTRAL_POWERS.name()}">Central Powers</a>
						<a href="${Alignments.ENTENTE.name()}">Entente</a>
					</div>
				</c:if>
				<c:if test="${alignment.alignment != Alignments.NEUTRAL}">
					<div class="title">Reputation</div>
					<div class="subtile">
						<div class="title">Current Reputation: ${home.foreign.getReputation(alignment.alignment)}</div>
						Reputation increases every turn by <fmt:formatNumber value="${home.getMaxReputation(alignment.alignment) * 0.05}" maxFractionDigits="0"/> until reaching a maximum of: ${home.getMaxReputation(alignment.alignment)}<br>
						Max Reputation affected by:
						<ul>
							<c:if test="${alignment.alignment == Alignments.CENTRAL_POWERS}">
								<c:forEach var="rep" items="${home.centralPowersReputation}">
									<c:if test="${rep.value != 0}">
										<li>
											<c:if test="${rep.value < 0}">
												<span class="negative">${rep.value}${rep.key.text}</span>
											</c:if>
											<c:if test="${rep.value > 0}">
												<span class="positive">+${rep.value}${rep.key.text}</span>
											</c:if>
										</li>
									</c:if>
								</c:forEach>
							</c:if>
							<c:if test="${alignment.alignment == Alignments.ENTENTE}">
								<c:forEach var="rep" items="${home.ententeReputation}">
									<c:if test="${rep.value != 0}">
										<li>
											<c:if test="${rep.value < 0}">
												<span class="negative">${rep.value}${rep.key.text}</span>
											</c:if>
											<c:if test="${rep.value > 0}">
												<span class="positive">+${rep.value}${rep.key.text}</span>
											</c:if>
										</li>
									</c:if>
								</c:forEach>
							</c:if>
						</ul>
					</div>
					<div class="title">Stockpiles</div>
					<div class="subtile">
							<%--@elvariable id="item" type="com.watersfall.clocgame.model.producible.Producibles"--%>
						<c:forEach var="item" items="${Alignment.ALLOWED_PRODUCIBLES}">
							<div class="title">
									${item.name()}:
								<c:set value="${alignment.getProducible(item)}" var="nullCheck"/>
								<c:if test="${nullCheck == null}">
									0
								</c:if>
								<c:if test="${nullCheck != null}">
									<fmt:formatNumber value="${alignment.getProducible(item)}"/>
								</c:if>
							</div>
							<div class="description">
								We Have: <fmt:formatNumber value="${home.getProducibleValue(item)}"/><br>
								Buy ${alignment.getTransactionAmount(item)} -
								<button onclick="alignmentTransaction('${alignment.alignment.name()}', '${item.name()}', 'buy')" class="blue">
									(<fmt:formatNumber value="${alignment.getProducibleBuyCost(item, home)}"/> reputation)
								</button>
								Sell ${alignment.getTransactionAmount(item)} -
								<button onclick="alignmentTransaction('${alignment.alignment.name()}', '${item.name()}', 'sell')" class="red">
									(<fmt:formatNumber value="${alignment.getProducibleSellCost(item, home)}"/> reputation)
								</button>
							</div>
						</c:forEach>
					</div>
				</c:if>
			</div>
		</div>
	</div>
<%@ include file="includes/bottom.jsp" %>