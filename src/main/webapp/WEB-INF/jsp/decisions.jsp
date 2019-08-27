<%@ include file="includes/default.jsp" %>
<html>
<%@ include file="includes/head.jsp" %>
<body>
<%@ include file="includes/header.jsp" %>
<script src="/js/decisions.js"></script>
<div class="main">
    <%@ include file="includes/results.jsp" %>
    <table id="policy">
        <tr>
            <th>Decision</th>
            <th>Description</th>
            <th>Options</th>
            <th>Description</th>
            <th>Apply</th>
        </tr>
        <tr>
            <td>Manpower</td>
            <td>Raise or lower manpower</td>
            <td>
                <select id="manpower" onchange="updateDesc('manpower')" class="select" ${result_policy.rows[0].manpower_change > 0 ? 'disabled' : ''}>
                    <option class="option" id="manpower_disarmed" ${result_policy.rows[0].manpower == 0 ? 'selected' : ''}>Disarmed Populace</option>
                    <option class="option" id="manpower_volunteer" ${result_policy.rows[0].manpower == 1 ? 'selected' : ''}>Volunteer Army</option>
                    <option class="option" id="manpower_drives" ${result_policy.rows[0].manpower == 2 ? 'selected' : ''}>Recruitment Drives</option>
                    <option class="option" id="manpower_draft" ${result_policy.rows[0].manpower == 3 ? 'selected' : ''}>Mandatory Draft</option>
                    <option class="option" id="manpower_scraping" ${result_policy.rows[0].manpower == 4 ? 'selected' : ''}>Extended Draft</option>
                </select>
            </td>
            <td>
                <p class="optionDesc" id="manpower_disarmed_desc" style="display:${result_policy.rows[0].manpower == 0 ? 'block' : 'none'};">Disarmed Populace</p>
                <p class="optionDesc" id="manpower_volunteer_desc" style="display:${result_policy.rows[0].manpower == 1 ? 'block' : 'none'};">Volunteer Army</p>
                <p class="optionDesc" id="manpower_drives_desc" style="display:${result_policy.rows[0].manpower == 2 ? 'block' : 'none'};">Recruitment Drives</p>
                <p class="optionDesc" id="manpower_draft_desc" style="display:${result_policy.rows[0].manpower == 3 ? 'block' : 'none'};">Mandatory Draft</p>
                <p class="optionDesc" id="manpower_scraping_desc" style="display:${result_policy.rows[0].manpower == 4 ? 'block' : 'none'};">Extended Draft</p>
            </td>
            <td>
                <button class="policyButton" onclick="decision('manpower')" ${result_policy.rows[0].manpower_change > 0 ? 'disabled' : ''}>Update</button>
            </td>
        </tr>
        <tr>
            <td>Food</td>
            <td>How much food do people deserve</td>
            <td>
                <select id="food" onchange="updateDesc('food')" class="select">
                    <option class="option" id="food_ration" ${result_policy.rows[0].food == 0 ? 'selected' : ''} ${result_policy.rows[0].food_change > 0 ? 'disabled' : ''}>Strict rationing</option>
                    <option class="option" id="food_normal" ${result_policy.rows[0].food == 1 ? 'selected' : ''} ${result_policy.rows[0].food_change > 0 ? 'disabled' : ''}>Normal</option>
                    <option class="option" id="food_free" ${result_policy.rows[0].food == 2 ? 'selected' : ''} ${result_policy.rows[0].food_change > 0 ? 'disabled' : ''}>Free Food</option>
                </select>
            </td>
            <td>
                <p class="optionDesc" id="food_ration_desc" style="display:${result_policy.rows[0].food == 0 ? 'block' : 'none'};">Strict rationing</p>
                <p class="optionDesc" id="food_normal_desc" style="display:${result_policy.rows[0].food == 1 ? 'block' : 'none'};">Normal</p>
                <p class="optionDesc" id="food_free_desc" style="display:${result_policy.rows[0].food == 2 ? 'block' : 'none'};">Free Food</p>
            </td>
            <td>
                <button class="policyButton" onclick="decision('food')">Update</button>
            </td>
        </tr>
    </table>
</div>
</body>
</html>